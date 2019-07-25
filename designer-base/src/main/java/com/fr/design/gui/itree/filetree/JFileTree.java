package com.fr.design.gui.itree.filetree;

import com.fr.design.i18n.Toolkit;
import com.fr.general.ComparatorUtils;
import com.fr.general.IOUtils;
import com.fr.stable.ArrayUtils;
import com.fr.stable.project.ProjectConstants;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;


/**
 * File Tree.
 */
public class JFileTree extends AbstractFileTree {

    private FileFilter fileFilter;

    public JFileTree() {
        this(null);
    }

    public JFileTree(FileFilter filter) {
        this.init(filter);
    }

    private void init(FileFilter filter) {
        this.fileFilter = filter;

        DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(Toolkit.i18nText("Fine-Design_Basic_My_Computer")));
        this.setModel(treeModel);

        this.putClientProperty("JTree.lineStyle", "Angled");

        this.addTreeExpansionListener(this);
        this.setCellRenderer(fileTreeCellRenderer);

        this.setRootVisible(false);
        this.setShowsRootHandles(true);
        this.setEditable(false);
    }

    public void setRootFile(File rootFile) {
        setRootFiles(new File[]{rootFile});
    }

    public void setRootFiles(File[] rootFiles) {
        if (ArrayUtils.getLength(rootFiles) == 0) {
            return;
        }

        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode rootTreeNode = (DefaultMutableTreeNode) defaultTreeModel.getRoot();
        rootTreeNode.removeAllChildren();

        for (File rootFile : rootFiles) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new RootFile(rootFile));
            rootTreeNode.add(node);

            if (rootFile.isDirectory()) {
                node.add(new DefaultMutableTreeNode(Boolean.TRUE));
            }
        }
        // richer:不是LocalEnv根本就不会运行到这儿
        defaultTreeModel.reload(rootTreeNode);

        if (rootFiles.length == 1) {
            File expandFile = rootFiles[0];
            this.selectFile(expandFile);
        }
    }

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }

    @Nullable
    public File getSelectedFile() {
        TreePath selectedTreePath = this.getSelectionPath();
        if (selectedTreePath == null) {
            return null;
        }

        DefaultMutableTreeNode currentTreeNode = this.getMutableTreeNode(selectedTreePath);
        StringBuilder fBuf = new StringBuilder();
        while (currentTreeNode != null) {
            // 如果已经到了根节点,直接退出.

            Object object = currentTreeNode.getUserObject();
            if (object instanceof RootFile) {
                // 当前文件.
                RootFile rootFileNode = (RootFile) object;
                return new File(rootFileNode.getFile() + fBuf.toString());
            }

            FileDirectoryNode nameNode = (FileDirectoryNode) object;
            fBuf.insert(0, nameNode.getName());
            fBuf.insert(0, "/");

            // 逐层返回
            currentTreeNode = (DefaultMutableTreeNode) currentTreeNode.getParent();
        }

        return null;
    }

    /**
     * 通过文件夹寻找展开路径
     *
     * @param currentFile 当前文件
     */
    public void selectFile(File currentFile) {
        if (currentFile == null) {
            return;
        }
        DefaultTreeModel m_model = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode rootTreeNode = (DefaultMutableTreeNode) m_model.getRoot();
        int rootChildCount = rootTreeNode.getChildCount();
        for (int i = 0; i < rootChildCount; i++) {
            DefaultMutableTreeNode rootChildTreeNode = (DefaultMutableTreeNode) rootTreeNode.getChildAt(i);
            RootFile rootLocalFile = (RootFile) rootChildTreeNode.getUserObject();
            File rootFile = rootLocalFile.getFile();
            // 是父子关系,开始找孩子.
            if (AbstractFileTree.isParentFile(rootFile, currentFile)) {
                Stack<String> nameStack = new Stack<>(); // 将所有的名字加入Stack.
                while (true) {
                    if (ComparatorUtils.equals(rootFile, currentFile)) {
                        break;
                    }
                    nameStack.push(currentFile.getName());
                    currentFile = currentFile.getParentFile();
                    if (currentFile == null) {
                        break;
                    }
                }
                DefaultMutableTreeNode curChildTreeNode = rootChildTreeNode;
                while (!nameStack.isEmpty()) {
                    String name = nameStack.pop();
                    this.expandTreeNode(curChildTreeNode);
                    for (int j = 0; j < curChildTreeNode.getChildCount(); j++) {
                        DefaultMutableTreeNode tmpChildTreeNode =
                                (DefaultMutableTreeNode) curChildTreeNode.getChildAt(j);
                        FileDirectoryNode tmpNameNode = (FileDirectoryNode) tmpChildTreeNode.getUserObject();
                        if (ComparatorUtils.equals(name, tmpNameNode.getName())) {
                            curChildTreeNode = tmpChildTreeNode;
                            // 选择当前的节点.
                            this.setSelectionPath(new TreePath(m_model.getPathToRoot(curChildTreeNode)));
                            break;
                        }
                    }
                }
                break;
            }
        }
        TreePath selectedTreePath = this.getSelectionPath();
        if (selectedTreePath != null) {
            this.scrollPathToVisible(selectedTreePath);
        }
    }

    /**
     * 列出当前所有的File
     *
     * @param currentTreeNode 当前文件节点
     * @return 当前节点下的所有File
     */
    @Override
    public FileDirectoryNode[] listFileNodes(DefaultMutableTreeNode currentTreeNode) {
        StringBuilder fBuf = new StringBuilder();
        while (currentTreeNode != null) {
            // 如果已经到了根节点,直接退出.
            Object object = currentTreeNode.getUserObject();
            if (object instanceof RootFile) {
                RootFile rootFileNode = (RootFile) object;
                // 当前文件. (rootFileNode + fBuf.toString = Path　　local地址)
                File currentFile = new File(rootFileNode.getFile() + fBuf.toString());
                // 列出当前文件的所有子文件,要判断下是否是系统保护的文件 能否打开. 打不开的话显示为null
                File[] files = currentFile.listFiles();
                // 如果文件列表为null 或者为File[0] = []返回null
                if (files == null) {
                    return new FileDirectoryNode[0];
                }
                List<FileDirectoryNode> fileNodeList = new ArrayList<>();
                for (File tmpFile : files) {
                    // 文件属性为隐藏的话  不放入列表
                    if (tmpFile.isHidden()) {
                        continue;
                    }
                    // 过滤只显示文件夹 并进行 名字简化
                    if (fileFilter.accept(tmpFile)) {
                        // newNode 传递 isDirectory属性 并且只显示文件夹名字
                        FileDirectoryNode newNode = FileDirectoryNode.createFileDirectoryNode(tmpFile);
                        fileNodeList.add(newNode);
                    }
                }
                // 节点加入列表
                FileDirectoryNode[] fileNodes = new FileDirectoryNode[fileNodeList.size()];
                fileNodeList.toArray(fileNodes);
                // 对文件夹进行排序
                Arrays.sort(fileNodes, new FileDirectoryNodeComparator());
                return fileNodes;
            }
            // 名字进行逐层反序的回加. 例:  Doload ==> C:\java\Doload ,返回到文件夹的path,因为有可能是String. 所以加上instanceof
            if (object instanceof FileDirectoryNode) {
                FileDirectoryNode nameNode = (FileDirectoryNode) object;
                fBuf.insert(0, nameNode.getName());
                fBuf.insert(0, "/");
            }
            // 逐层返回
            currentTreeNode = (DefaultMutableTreeNode) currentTreeNode.getParent();
        }
        return new FileDirectoryNode[0];
    }

    /**
     * cellRenderer: tree中显示文件的类型图标
     */
    private DefaultTreeCellRenderer fileTreeCellRenderer = new DefaultTreeCellRenderer() {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded,
                    leaf, row, hasFocus);

            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            StringBuilder fBuf = new StringBuilder();
            while (treeNode != null) {
                Object userObj = treeNode.getUserObject();
                if (userObj instanceof RootFile) {
                    RootFile rootFileNode = (RootFile) userObj;
                    // 当前文件的全部路径. (rootFileNode + fBuf.toString = Path　　local地址)
                    File currentFile = new File(rootFileNode.getFile() + fBuf.toString());
                    FileSystemView view = FileSystemView.getFileSystemView();
                    // File的全部路径.
                    // 得到本地tree图标
                    Icon tmpIcon = view.getSystemIcon(currentFile);
                    if (currentFile.isDirectory() && fBuf.length() > 0) {
                        tmpIcon = IOUtils.readIcon("/com/fr/design/images/gui/folder.png");
                    }
                    this.setIcon(tmpIcon);
                    this.setName(null);
                    Font oldFont = this.getFont();
                    if (ComparatorUtils.equals(currentFile.getName(), ProjectConstants.WEBINF_NAME)) {
                        this.setForeground(Color.blue);
                        this.setFont(new Font(oldFont.getName(), Font.BOLD, oldFont.getSize()));
                    } else {
                        this.setFont(new Font(oldFont.getName(), Font.PLAIN, oldFont.getSize()));
                    }
                }
                // 名字进行逐层反序的回加. 例:  Doload ==> C:\java\Doload
                if (userObj instanceof FileDirectoryNode) {
                    FileDirectoryNode nameNode = (FileDirectoryNode) userObj;
                    fBuf.insert(0, nameNode.getName());
                    fBuf.insert(0, "/");
                }
                // 逐层往上 倒退返回
                treeNode = (DefaultMutableTreeNode) treeNode.getParent();
            }
            return this;
        }
    };

    /**
     * 对文件夹进行排序 先文件夹 然后各种类型文件
     *
     * @author kunsnat
     */
    public class FileDirectoryNodeComparator implements Comparator<FileDirectoryNode> {
        /**
         * This method should return > 0 if v1 is greater than v2, 0 if
         * v1 is equal to v2, or < 0 if v1 is less than v2.
         * It must handle null values for the comparison values.
         * 如上所述
         *
         * @param v1 comparison value.值1
         * @param v2 comparison value.值2
         * @return < 0, 0, or > 0 for v1<v2, v1==v2, or v1>v2 .值1大于值2返回大于0，相等返回0，小于和大于相反
         */
        @Override
        public int compare(FileDirectoryNode v1, FileDirectoryNode v2) {
            //noinspection Duplicates
            if (v1.isDirectory()) {
                if (v2.isDirectory()) {
                    return v1.getName().toLowerCase().compareTo(v2.getName().toLowerCase());
                } else {
                    return -1;
                }
            } else {
                if (v2.isDirectory()) {
                    return 1;
                } else {
                    return v1.getName().toLowerCase().compareTo(v2.getName().toLowerCase());
                }
            }
        }
    }
}