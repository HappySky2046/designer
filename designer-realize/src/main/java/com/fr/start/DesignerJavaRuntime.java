package com.fr.start;

import com.fr.design.os.impl.SupportOSImpl;
import com.fr.general.ComparatorUtils;
import com.fr.general.GeneralContext;
import com.fr.general.GeneralUtils;
import com.fr.general.IOUtils;
import com.fr.locale.InterProviderFactory;
import com.fr.log.FineLoggerFactory;
import com.fr.process.engine.core.AbstractJavaRuntime;
import com.fr.stable.ArrayUtils;
import com.fr.stable.StableUtils;
import com.fr.stable.StringUtils;
import com.fr.stable.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 设计器Java运行环境
 *
 * @author hades
 * @version 10.0
 * Created by hades on 2019/9/22
 */
public class DesignerJavaRuntime extends AbstractJavaRuntime {

    private static final String DOT = ".";
    private static final String REMOTE_DEBUG = "-agentlib:jdwp=transport=dt_socket";
    private static final String INSTALL4J = ".install4j";
    private static final String JAVA_EXEC = "java";
    private static final String WIN_JRE_BIN = StableUtils.pathJoin("jre", "bin");
    private static final String MAC_JRE_BIN = StableUtils.pathJoin("jre.bundle", "Contents", "Home", "jre", "bin");
    private static final String BIN_HOME = StableUtils.pathJoin(StableUtils.getInstallHome(), "bin");
    private static final String LOGO_PATH = StableUtils.pathJoin(BIN_HOME, "logo.png");
    private static final String DOCK_OPTIONS = "-Xdock:icon=" + LOGO_PATH;
    private static final String DOCK_NAME_OPTIONS = "-Xdock:name=" + FineDesigner.class.getSimpleName();
    private static final String WIN_VM_OPTIONS_PATH =  StableUtils.pathJoin(BIN_HOME, "designer.vmoptions");
    private static final String[] DEBUG_OPTIONS = new String[]{"-Dfile.encoding=UTF-8", "-Xmx2048m"};

    static {
        try {
            if (SupportOSImpl.DOCK_ICON.support()) {
                IOUtils.copy(DesignerJavaRuntime.class.getResourceAsStream("/com/fr/design/icon/logo.png"), "logo.png", new File(BIN_HOME));
            }
        } catch (IOException ignore) {
        }
    }

    private static final DesignerJavaRuntime INSTANCE = new DesignerJavaRuntime();

    public static DesignerJavaRuntime getInstance() {
        return INSTANCE;
    }

    /**
     * 远程调试不走启动守护
     * @return
     */
    public boolean isInValidVmOptions() {
        String[] options = getJvmOptions();
        for (String op : options) {
            if (op.startsWith(REMOTE_DEBUG)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getJavaExec() {
        String installHome = StableUtils.getInstallHome();
        if (!isInstallVersion()) {
            return JAVA_EXEC;
        }
        if (OperatingSystem.isWindows()) {
            return StableUtils.pathJoin(installHome, WIN_JRE_BIN, JAVA_EXEC);
        }
        if (OperatingSystem.isMacos()) {

            return StableUtils.pathJoin(installHome, INSTALL4J, MAC_JRE_BIN, JAVA_EXEC);
        }
        if (OperatingSystem.isUnix()) {
            return StableUtils.pathJoin(installHome, WIN_JRE_BIN, JAVA_EXEC);

        }
        return StringUtils.EMPTY;
    }

    private boolean isInstallVersion() {
        return !ComparatorUtils.equals(GeneralUtils.readFullBuildNO(), InterProviderFactory.getProvider().getLocText("Fine-Core_Basic_About_No_Build"));
    }


    /**
     * 非安装版本需要添加下内存参数
     * 工程中可根据需要修改
     *
     * @return 参数
     */
    @Override
    public String[] getJvmOptions() {
        if (isInstallVersion()) {
            String[] options = super.getJvmOptions();
            // win下环境变量 存在错误的设置会导致问题 直接读vmoptions
            if (SupportOSImpl.VM_OPTIONS_ADAPTER.support()) {
                List<String> optionList = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(new File(WIN_VM_OPTIONS_PATH)))) {
                    String option = null;
                    while ((option = reader.readLine()) != null) {
                        optionList.add(option.trim());
                    }
                } catch (Exception e) {
                    FineLoggerFactory.getLogger().error(e.getMessage(), e);
                    return DEBUG_OPTIONS;
                }
                if (!optionList.isEmpty()) {
                    return optionList.toArray(new String[0]);
                }
            }
            if (SupportOSImpl.DOCK_ICON.support()) {
                options = ArrayUtils.addAll(options, DOCK_OPTIONS, DOCK_NAME_OPTIONS);
            }
            FineLoggerFactory.getLogger().debug("Vm Options: " + Arrays.toString(options));
            return options;
        } else {
            return DEBUG_OPTIONS;
        }

    }
}
