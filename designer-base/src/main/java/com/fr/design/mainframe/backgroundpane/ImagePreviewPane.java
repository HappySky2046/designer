package com.fr.design.mainframe.backgroundpane;

/*
 * Copyright(c) 2001-2010, FineReport Inc, All Rights Reserved.
 */

import com.fr.base.BaseUtils;
import com.fr.base.GraphHelper;
import com.fr.base.Style;
import com.fr.design.constants.UIConstants;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.iscrollbar.UIScrollBar;
import com.fr.design.style.background.image.ImagePreviewer;
import com.fr.general.ImageWithSuffix;

import com.fr.stable.Constants;
import com.fr.stable.CoreGraphHelper;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * The pane use to preview image
 */
public class ImagePreviewPane extends JComponent implements Scrollable, ImagePreviewer {
    private static final int LABEL_DELTA_X = 10;
    private static final int LABEL_DELTA_Y = 20;
    private static final int LABEL_HEIGHT = 20;
    private static final int INCRE_DELTA = 10;
    private ImageWithSuffix image = null;
    // carl:image style
    private Style imageStyle = null;
    private int imageWidth = 0;
    private int imageHeight = 0;

    private List<ChangeListener> changeListenerList = new ArrayList<ChangeListener>();
    private UILabel sizeLabel;
    private boolean isLoading = false;

    public ImagePreviewPane() {
        sizeLabel = new UILabel();
        this.add(sizeLabel);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        this.setLayout(new BorderLayout());
        this.add(new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isLoading) {
                    g.drawString(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_Image_Loading"), getWidth() / 2 - 25, getHeight() / 2);
                    return;
                }
                // draw image.
                if (getImage() != null) {
                    // carl:让imagePreviewPane能预览样式
                    if (getImageStyle() == null) {
                        g.drawImage(getImage(), 0, 0, this);
                    } else {
                        if (getImage().getWidth(this) > getWidth() || getImage().getHeight(this) > getHeight()) {
                            GraphHelper.paintImageMoved(g, this.getWidth(), this.getHeight(), getImage(), Constants.IMAGE_ADJUST, BaseUtils.getAlignment4Horizontal(getImageStyle()),
                                    getImageStyle().getVerticalAlignment(), -1, -1, 0, 0, false, UIConstants.NORMAL_BACKGROUND);
                        } else {
                            int totalwidth = this.getWidth();
                            int totalheight = this.getHeight();
                            g.drawImage(getImage(), totalwidth / 2 - imageWidth / 2, totalheight / 2 - imageHeight / 2, this);
                        }
                    }
                }
            }
        }, BorderLayout.CENTER);
        // this.setToolTipText("View Image");
    }

    /**
     * Return image
     */
    public Image getImage() {
        return image == null ? null : this.image.getImage();
    }

    /**
     * Return ImageWithSuffix
     */
    public ImageWithSuffix getImageWithSuffix() {
        return this.image;
    }

    /**
     * Set image.
     *
     * @param image the new image.
     */
    @Override
    public void setImageWithSuffix(ImageWithSuffix image) {
        this.image = image;
        // need to reset the size of JViewPort.
        if (this.image == null || this.image.getImage() == null) {
            if (this.getParent() instanceof JViewport) {
                UIScrollBar tmpJScrollBar = new UIScrollBar(UIScrollBar.HORIZONTAL);
                Dimension newDimension = new Dimension(this.getSize().width - tmpJScrollBar.getPreferredSize().height, this.getSize().height
                        - tmpJScrollBar.getPreferredSize().height);
                ((JViewport) this.getParent()).setPreferredSize(newDimension);
                (this.getParent()).setSize(newDimension);
                ((JViewport) this.getParent()).setMinimumSize(newDimension);
                ((JViewport) this.getParent()).setMaximumSize(newDimension);
            }
            sizeLabel.setText(null);
        } else {
            isLoading = false;
            // wait for the size of image.
            CoreGraphHelper.waitForImage(image);

            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
            int totalwidth = ImagePreviewPane.this.getWidth();
            int totalheight = ImagePreviewPane.this.getHeight();
            String text = imageWidth + "x" + imageHeight;
            sizeLabel.setText(text);
            FontMetrics cellFM = this.getFontMetrics(getFont());
            int width = cellFM.stringWidth(text);
            sizeLabel.setBounds(totalwidth - width - LABEL_DELTA_X, totalheight - LABEL_DELTA_Y, width, LABEL_HEIGHT);
        }
        fireChangeListener();
        this.revalidate();
    }

    @Override
    public void showLoading() {
        isLoading = true;
        setImage(null);
        repaint();
    }

    /**
     * Paint component.
     */

    @Override
    public Dimension getPreferredSize() {
        if (this.image == null) {
            return super.getPreferredSize();
        }

        return new Dimension(imageWidth, imageHeight);
    }

    /**
     * Add change listener.
     */
    public void addChangeListener(ChangeListener changeListener) {
        changeListenerList.add(changeListener);
    }

    /**
     * fire change listener when image changed.
     */
    private void fireChangeListener() {
        if (this.changeListenerList.size() > 0) {
            ChangeEvent evt = new ChangeEvent(this);

            for (int i = 0; i < changeListenerList.size(); i++) {
                changeListenerList.get(i).stateChanged(evt);
            }
        }
    }

    // --- Scrollable methods ---------------------------------------------

    /**
     * Returns the preferred size of the viewport for a view component. This is
     * implemented to do the default behavior of returning the preferred size of
     * the component.
     *
     * @return the <code>preferredSize</code> of a <code>JViewport</code> whose
     * view is this <code>Scrollable</code>
     */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    /**
     * Components that display logical rows or columns should compute the scroll
     * increment that will completely expose one new row or column, depending on
     * the value of orientation. Ideally, components should handle a partially
     * exposed row or column by returning the distance required to completely
     * expose the item.
     * <p/>
     * The default implementation of this is to simply return 10% of the visible
     * area. Subclasses are likely to be able to provide a much more reasonable
     * value.
     *
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code> or
     *                    <code>SwingConstants.HORIZONTAL</code>
     * @param direction   less than zero to scroll up/left, greater than zero for
     *                    down/right
     * @return the "unit" increment for scrolling in the specified direction
     * @throws IllegalArgumentException for an invalid orientation
     * @see javax.swing.JScrollBar#setUnitIncrement
     */
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch (orientation) {
            case SwingConstants.VERTICAL:
                return visibleRect.height / INCRE_DELTA;
            case SwingConstants.HORIZONTAL:
                return visibleRect.width / INCRE_DELTA;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    /**
     * Components that display logical rows or columns should compute the scroll
     * increment that will completely expose one block of rows or columns,
     * depending on the value of orientation.
     * <p/>
     * The default implementation of this is to simply return the visible area.
     * Subclasses will likely be able to provide a much more reasonable value.
     *
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code> or
     *                    <code>SwingConstants.HORIZONTAL</code>
     * @param direction   less than zero to scroll up/left, greater than zero for
     *                    down/right
     * @return the "block" increment for scrolling in the specified direction
     * @throws IllegalArgumentException for an invalid orientation
     * @see javax.swing.JScrollBar#setBlockIncrement
     */
    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch (orientation) {
            case SwingConstants.VERTICAL:
                return visibleRect.height;
            case SwingConstants.HORIZONTAL:
                return visibleRect.width;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    /**
     * Returns true if a viewport should always force the width of this
     * <code>Scrollable</code> to match the width of the viewport. For example a
     * normal text view that supported line wrapping would return true here,
     * since it would be undesirable for wrapped lines to disappear beyond the
     * right edge of the viewport. Note that returning true for a
     * <code>Scrollable</code> whose ancestor is a <code>JScrollPane</code>
     * effectively disables horizontal scrolling.
     * <p/>
     * Scrolling containers, like <code>JViewport</code>, will use this method
     * each time they are validated.
     *
     * @return true if a viewport should force the <code>Scrollable</code>s
     * width to match its own
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        if (getParent() instanceof JViewport) {
            return (getParent().getWidth() > getPreferredSize().width);
        }
        return false;
    }

    /**
     * Returns true if a viewport should always force the height of this
     * <code>Scrollable</code> to match the height of the viewport. For example
     * a columnar text view that flowed text in left to right columns could
     * effectively disable vertical scrolling by returning true here.
     * <p/>
     * Scrolling containers, like <code>JViewport</code>, will use this method
     * each time they are validated.
     *
     * @return true if a viewport should force the Scrollables height to match
     * its own
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport) {
            return (getParent().getHeight() > getPreferredSize().height);
        }
        return false;
    }

    @Override
    public void setImageStyle(Style imageStyle) {
        this.imageStyle = imageStyle;
    }

    @Override
    public void setImage(Image image) {

        setImageWithSuffix(ImageWithSuffix.build(image));

    }

    public Style getImageStyle() {
        return imageStyle;
    }
}