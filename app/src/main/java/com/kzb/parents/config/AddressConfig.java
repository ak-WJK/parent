package com.kzb.parents.config;

/**
 * Created by wanghaofei on 16/11/29.
 */

public class AddressConfig {


    //获取学校信息
    public static final String SET_GET_SCHOOL_URL = "api.php/My/getSchool";
    //支付宝订单信息
    public static final String ORDER_INFO_URL = "api.php/Alipay/sdk";

    //微信订单信息
    public static final String WXORDER_INFO_URL = "api.php/Alipay/wxPay";

    //获取金额列表
    public static final String ORDER_INFO_LIST_URL = "api.php/Alipay/goodsList";
    //获取订单状态
    public static final String ORDER_STATUS_URL = "api.php/Alipay/getStatus";

    //获取区域
    public static final String IMPROVE_QUYU_URL = "api.php/My/getDistinct";
    //获取市
    public static final String IMPROVE_CITY_URL = "api.php/My/getCity";
    //获取省
    public static final String IMPROVE_PROVINCE_URL = "api.php/My/getArea";
    //提交认证数据
    public static final String RENZHENG_URL = "api.php/My/doPass";

    //获取审核状态
    public static final String SHENHE_STATUS_URL = "api.php/My/getStatus";
    //学校认证，年级
    public static final String RENZHENG_YEARS_URL = "api.php/My/getYears";

    //文章地址
    public static final String ARTICLE_URL = "http://t.kaozhibao.com/console.php/MobileMessage/lunbodetail/id/";

    //登录：家长端登录
    public static final String LOGIN_PAR_URL = "api.php/Index/login";


    //登录
    public static final String LOGIN_URL = "api.php/Login/login";

    //获取验证码
    public static final String CODE_URL = "api.php/Index/msg";
    //main page
    public static final String MAIN_PAGE_URL = "api.php/Sys/home";
    //测试
    public static final String TEST_PAGE_URL = "api.php/Sys/test";
    //考试
    public static final String EXAM_PAGE_URL = "api.php/Sys/exam";
    //学习
    public static final String LEARN_PAGE_URL = "api.php/Sys/study";
    //设置
    public static final String SET_PAGE_URL = "api.php/Sys/set";
    //完善信息,获取年级列表
    public static final String IMPROVE_YEAR_URL = "api.php/My/getYear";
    //完善信息,获取班级列表
    public static final String IMPROVE_CLASS_URL = "api.php/My/getGrade";
    //完善信息,提交个人信息
    public static final String COMIT_IMPROVE_URL = "api.php/My/doComplete";
    //课程列表
    public static final String COURSE_URL = "api.php/Subject/subjectList";
    //
    public static final String SAVE_COURSE_URL = "api.php/Subject/changetSub";

    //诊断章节列表
    public static final String DIAGNOSE_CHAPTER_LIST = "api.php/Zhenduan/charperList";
    //全科章节列表
    public static final String DIAGNOSE_QUANKE_LIST = "api.php/Zhenduan/examList";
    //章节诊断，题目(废弃)
    public static final String CHAPTER_QUESTION_LIST = "api.php/Zhenduan/chapter";
    //真题诊断，题目
    public static final String ZHENTI_QUESTION_LIST = "api.php/Zhenduan/exam";
    //全科诊断，题目(废弃)
    public static final String QUANKE_QUESTION_LIST = "api.php/Zhenduan/subjectTest";
    //诊断，全科诊断
    public static final String CHAPTER_QUESTION = "api.php/Zhenduan/zhenduan";
    //全科诊断
    public static final String CHAPTER_QUESTION_QK = "api.php/Zhenduan/quanke";
    //章节诊断
    public static final String CHAPTER_QUESTION_ZJ = "api.php/Zhenduan/zhangjie";
    //考试提交
    public static final String EXAM_SUBMIT = "api.php/Test/subRes";
    //强化考试提交
    public static final String EXAM_S_SUBMIT = "api.php/Knowledge/saveUnknow";
    //获取考试报告
    public static final String GET_TEST_INFO = "api.php/Testinfo/testInfo";
    //获取题目掌握情况
    public static final String GET_TEST_KNOWLEDGE = "api.php/Testinfo/testKnowledge";
    //获取题目完成情况
    public static final String GET_TEST_QUES = "api.php/Testinfo/testQuesInfo";

    //针对项目
    public static final String EXAM_ZJ_ZHENDUI = "api.php/Test/ungetByz";

    //新增题目完成情况列表接口
    public static final String GET_TEST_QUES_LIST = "api.php/Testinfo/testQuestionList";
    //诊断列表
    public static final String DIAGNOSE_LIST = "api.php/Zhenduan/charperList";
    //快报列表
    public static final String LETTER_LIST_URL = "api.php/Msg/msgList";
    //快报详情
    public static final String LETTER_DETAIL_URL = "api.php/Msg/content";
    //消息列表
    public static final String MSG_LIST_URL = "api.php/Msg/msg";
    //课程学习
    public static final String COURSE_LIST_URL = "api.php/Knowledge/xuexi";
    //课程学习，未学习
    public static final String COURSE_NO_LEARN_LIST_URL = "api.php/Knowledge/xuexiunlearn";
    //课程学习，已学习
    public static final String COURSE_LEARN_LIST_URL = "api.php/Knowledge/xuexilearn";
    //强化提高
    public static final String STRENGTH_ADD_LIST_URL = "api.php/Knowledge/qianghua";
    //强化提高
    public static final String STRENGTH_No_LIST_URL = "api.php/Knowledge/unqianghua";
    //推送地址(目前做成主动请求的形式)
    public static final String PUSH_URL = "api.php/Msg/pushMsg";
    //已接强化提高
//    public static final String STRENGTH_YES_LIST_URL = "api.php/Knowledge/alreadyqianghua";
    //验证强化知识点
    public static final String CHECK_STRENGTH_URL = "api.php/Knowledge/xunlian";
    //将课程学习，知识点加入到强化提高的知识点中
    public static final String ADD_CL_SL = "api.php/Knowledge/toUnknow";
    //课程学习，详情，知识点学习
    public static final String COURSE_DETAIL_URL = "api.php/Knowledge/learnKnowledge";
    //强化提高
    public static final String STRENGTH_LIST_URL = "api.php/Knowledge/intensify";
    //错题本
    public static final String WRONG_LIST_URL = "api.php/Wrong/wrong";
    //错题本详情
    public static final String WRONG_DETAIL_URL = "api.php/Wrong/wrongQues";
    //章节排名
    public static final String RANK_ZHANG_URL = "api.php/Rank/testRank";
    //全科排名
    public static final String RANK_ALL_URL = "api.php/Rank/subjectRank";
    //章节纪录
    public static final String GET_CHART_RECORD = "api.php/Zhenduan/chapterRec";
    //全科纪录
    public static final String GET_QUANKE_RECORD = "api.php/Zhenduan/subRec";
    //真题纪录
    public static final String GET_ZHENTI_RECORD = "api.php/Zhenduan/examRec";

    //章节未完成
    public static final String EXAM_ZJ_UN_FINISH = "api.php/Test/chaUnFinish";
    //章节已完成
    public static final String EXAM_ZJ_FINISH = "api.php/Test/chaFinish";
    //全科未完成
    public static final String EXAM_QK_UN_FINISH = "api.php/Test/subUnFinish";
    //全科已完成
    public static final String EXAM_QK_FINISH = "api.php/Test/subFinish";
    //章节考试，报告列表
    public static final String ZJ_REPORT_LIST = "api.php/Test/chaperReport";
    //全科考试，报告列表
    public static final String QK_REPORT_LIST = "api.php/Test/subReport";
    //考试，考试
    public static final String KAOSHI_EXAM_START = "api.php/Test/test";
    //PK
    public static final String PK_LIST = "api.php/Pk/pkList";
    //诊断须知
    public static final String NEED_KENO_DIAGNOSE = "api.php/Zhenduan/zhenduanInfo";
    //真题诊断须知
    public static final String NEED_KENO_ZHENTI = "api.php/Zhenduan/examInfo";
//    考试须知
    public static final String NEED_KENO_EXAM = "api.php/Test/testInfo";
//    public static final String MSG_LIST_URL = "api.php/Msg/msg";
    public static final String CHECK_VERSION = "api.php/Sys/edition";
    //完成学习
    public static final String LEARN_OVER = "api.php/Knowledge/doLearn";
    //轮播地址
    public static final String MAIN_LUNBO = "api.php/Index/lunbo";
    //获取报告总数
    public static final String BAOGAO_TOTAL_NUM = "api.php/Zhenduan/testNum";
    public static final String GET_TEST_QUES_LIST_TWO = "api.php/testinfo/testPaper";
}
