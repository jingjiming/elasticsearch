package com.alpha.common.constant;

/**
 * Created by kids on 2019/2/20.
 */
public class RedisConstants {

    /* ----------------------------- 水雨情 start ---------------------------------*/
    /**
     * 1.预警信息
     */
    public static final String EARLY_WARNING_INFO = "hydroinfo:earlyWarningInfo";

    /**
     * 2.每日水雨情信息
     */
    public static final String HYDROINFO_BY_DAYS = "hydroinfo:hydroinfoByDays";

    /**
     * 3.超警信息
     */
    public static final String SURPASS_WARNING_INFO = "hydroinfo:surpassWarningInfo";

    /**
     * 4.全国重点河道站实时水情
     */
    public static final String VITAL_RIVER_INFO = "hydroinfo:vitalRiverInfo";

    /**
     * 5.全国重点水库站实时水情
     */
    public static final String VITAL_RSVR_INFO = "hydroinfo:vitalRsvrInfo";

    /**
     * 6.洪旱告警-干旱
     */
    public static final String DROUGHT_WARNING = "hydroinfo:droughtWarning";

    /**
     * 7.洪旱告警-台风
     */
    public static final String TYPHOON_WARNING = "hydroinfo:typhoonWarning";

    /**
     * 8.洪旱告警-暴雨
     */
    public static final String RAINSTORM_WARNING = "hydroinfo:rainstormWarning";

    /**
     * 9.洪旱告警-卫星云图
     */
    public static final String SATELLITE_CLOUD_IMAGE = "hydroinfo:satelliteCloudImage";

    /**
     * 10.大江大河
     */
    public static final String GREAT_RIVER = "hydroinfo:greatRiver";

    /**
     * 11.大型水库
     */
    public static final String GREAT_RSVR = "hydroinfo:greatRsvr";

    /**
     * 12.重点水雨情信息
     */
    public static final String POINT_INFO = "hydroinfo:pointInfo";

    /**
     * 13.日雨量图
     */
    public static final String NATIONAL_DAILY_RAINFALL = "hydroinfo:nationalDailyRainfall";


    /* ----------------------------- 水雨情 end ---------------------------------*/


    /* ----------------------------- 答题 start ---------------------------------*/
    /**
     * 答卷暂存
     */
    public static final String DT_TEMPORARY = "dt:temporary:";

    /* ----------------------------- 答题 end -----------------------------------*/


    /* ----------------------------- 水雨情webservice服务 start -----------------------------------*/

    public static final String VITAL_SYQAPI_RIVER = "hydroinfo:vitalSyqRiver";
    public static final String VITAL_SYQAPI_RSVR = "hydroinfo:vitalSyqRsvr";

    /* ----------------------------- 水雨情webservice服务 end -----------------------------------*/
}
