package com.fr.design.onlineupdate.domain;

/**
 * Created by XINZAI on 2018/8/21.
 */

import java.awt.Color;

/**
 * 更新升级的常量
 */
public interface UpdateConstants {

    String APPS_FOLDER_NAME = "webapps";

    int CONNECTION_TIMEOUT = 1000 * 5;
    Color BAR_COLOR = new Color(0x3384F0);

    String CHANGELOG_X_START = "2018-07-11";

    String DEFAULT_APP_NAME = "FineReport";
    String DOWNLOAD_DIR = "update";
    String DESIGNER_BACKUP_DIR = "designerbackup";

    String UPDATE_CACHE_CONFIG_X = "updateCacheConfig10";
    String UPDATE_CACHE_INFO_X = "updateCacheInfo10";


    int BYTE = 153600;

    String[] JARS_FOR_SERVER_X = new String[]{
            "fine-activator-10.0.jar",
            "fine-core-10.0.jar",
            "fine-report-engine-10.0.jar",
            "fine-decision-10.0.jar",
            "fine-decision-report-10.0.jar",
            "fine-schedule-10.0.jar",
            "fine-schedule-report-10.0.jar",
            "fine-swift-log-adaptor-10.0.jar",
            "fine-webui-10.0.jar",
            "fine-datasource-10.0.jar",
            "fine-third-10.0.jar",
            "fine-accumulator-10.0.jar"
    };

    String[] JARS_FOR_DESIGNER_X = new String[]{
            "fine-report-designer-10.0.jar",
            "aspectjrt.jar"
    };


    String[] LOG_TYPE = new String[]{
            "REPORT", "MOBILE", "CHART", "PFC", "BI"
    };

}