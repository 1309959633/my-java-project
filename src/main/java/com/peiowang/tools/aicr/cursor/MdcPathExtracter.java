package com.peiowang.tools.aicr.cursor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import it.unimi.dsi.fastutil.Hash;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class MdcPathExtracter {

    public static final HashMap<Integer, String> projectMap = new HashMap<>() {{
        put(36673, "cedruschen/script");
        put(41648, "QQNews_iOS/QQNews");
        put(46551, "mmpay-xmp/small_book/small_book/receipt_assist");
        put(56464, "wxbizpoc/mmbizpoc");
        put(68220, "xiaoyue/xiaoyue_miniapp");
        put(80267, "wg/QTL_RN");
        put(81038, "tmedev/tme_music_manage_frontend");
        put(83584, "WGFE/blockly-mini-game");
        put(95924, "mmpay-xmp/wxpay/overseas/oversea_giftPacks");
        put(103976, "ptuand/android_zebra_proj");
        put(137770, "mgplat/fcm_mg");
        put(138698, "kfportal/self_help");
        put(166119, "mmpay_wxpay/mch_sec");
        put(208068, "webb-backup/webbbackupnew");
        put(210482, "wxg-bigdata/fisher-group/fisher");
        put(248221, "ads/main");
        put(255827, "karaoke-web/pc_room");
        put(256263, "wxg-xwatt/xconfig");
        put(283110, "fit-front/ent-wallet-mgmt-platform");
        put(286316, "gaccobzhang/task");
        put(314174, "wxg-infra-weops/fbf/webdefinderweb");
        put(334188, "mmpay_XDC-P/XDC");
        put(348297, "wxg-xwatt/xoperation");
        put(388324, "FE/UNI_operate");
        put(411683, "svip/vdesk/im_logic");
        put(411708, "svip/vdesk/customer_manage");
        put(418226, "xinyue_app/flutter_mine");
        put(440344, "cfe/linglong/videocms");
        put(445015, "riemann/realtime_calc/uostream");
        put(453711, "timjlin/giraffe");
        put(458788, "svip/vdesk/approval_manage");
        put(471545, "mmpay-xmp/wxpay/kf/HelpCenter");
        put(497664, "wxbizpoc/mmbizpublicpocwap");
        put(504533, "mmecadmin/dbmig_script");
        put(534777, "red/guildsvr");
        put(589756, "mmpay_nodejs/wxpay/overseas");
        put(592926, "wxg-bigdata/pulsar/pulsar-manager-new");
        put(597739, "mmpay_nodejs/XSOA-P/XSOA");
        put(598719, "march/pipe/transfer");
        put(604036, "svip/common");
        put(647002, "wxpayuserinfo/mmpayuserrollui");
        put(658315, "jlib/jlib_proj");
        put(666066, "segmentedCommunity/clsq-h5");
        put(670126, "galileo/galileo");
        put(672106, "proz_team/engine_src");
        put(680549, "mmpay_nodejs/XMeta-P/XContract");
        put(739525, "ppd-remit/wisefx-standard-collection");
        put(742708, "wxgpa/fe/weishen");
        put(780849, "gerryyang/jlib_proj");
        put(789224, "mmpay-xmp/sjt/sjt_merchant_service/receipt_professional_mp");
        put(793863, "tss_data_team/andata-rt");
        put(795951, "wepay_fe/smbpd/sjt-payment-h5");
        put(809135, "fit-front/wqf-miniprogram");
        put(837383, "musicfe-c/qqmusic/basic/ssr");
        put(860652, "tce-platform/pd/tcenterapi");
        put(868230, "QAdSDK/QAdSDK-iOS");
        put(878334, "mmpay_nodejs/XConfig-P/XConfig");
        put(886654, "csig_datacenter/go/worksheet-server");
        put(887617, "xyfe/simple-pages-repo");
        put(903597, "wxg-mp/mmec");
        put(909174, "samleyluo/docs");
        put(912507, "xy-vip/service/action-srv");
        put(921995, "ugit/ue/UEGitPlugin");
        put(933934, "dcos/tdc");
        put(946154, "ola/olab-all");
        put(949301, "bas/web-ui");
        put(963117, "wx_fed/service/raven/mmravenadminmeshnode");
        put(971978, "TIMIJ6_svn/PMGame_proj");
        put(973795, "FIT-Cloud/cloud-openapi-server");
        put(973914, "mateochen/cloud-openapi-server");
        put(979478, "ied_svn/ied_nssclient_rep");
        put(982867, "wxpayfaceiot/wepalm-sdk-demo");
        put(995319, "svip/svip_whitelist");
        put(1000020, "OSGame/Client_proj");
        put(1015856, "midas_website/midasbuy_activity_materials");
        put(1018246, "wx_fed/base/sticker/mmemoticonmgrweb");
        put(1063078, "td/online_rd/front_end_ui_comp_lib/mis");
        put(1065736, "wx_fed/base/sticker/liteapp-sticker-banner");
        put(1065946, "xiaoyue/gbot-go/gbot-enwechat");
        put(1067357, "mmpay_wxpay/c2b_pay");
        put(1088826, "lctlib/fuservice-norm-ao");
        put(1096994, "karaoke-web/forest-material-kg");
        put(1099231, "developer/ai");
        put(1101332, "lctlib/lct-java/lct-arch-common");
        put(1107477, "omgweb/mobile/gold-coin");
        put(1107492, "qimei/qimeisdk-web");
        put(1108573, "mmpay_online_rd/liteapp_devops");
        put(1111017, "o0O0o/tools");
        put(1115326, "tmedev_web/dnf-webserver");
        put(1117095, "osgame-client/CoDevDataHub");
        put(1150827, "TME-Interaction-Live-WEB/universal-web");
        put(1151659, "wx_fed/service/wxalite/mmwxaliteops");
        put(1153209, "QQNews_Android/QnCore");
        put(1153520, "lowcode/logic/logic-editor/editor-sdk");
        put(1156255, "camp_overseas/upstream-sdk");
        put(1158386, "cloud-mt/fireeye/metabase");
        put(1159749, "simonghuang/tnebula-tedge");
        put(1177512, "rocphwang/tencent");
        put(1185857, "wx_fed/module/bsdp");
        put(1206697, "CloudMonitorFrontend/tea-sdk-monitor-prom");
        put(1207646, "xyfe/bot-scrm-manager");
        put(1210603, "xiaoyue_platform/priv-domain");
        put(1214322, "tmedev_web/dnf-frontend");
        put(1217965, "svip/station/svip_station");
        put(1220712, "idc/facility/facility-web");
        put(1222954, "QBase/lcap/app-template");
        put(1225642, "ffe/capital/fmpReg");
        put(1228058, "seanxxchen/wespeedtestsg");
        put(1231380, "mmec_mmec/shop_fund");
        put(1232509, "feone/o2/flux-site");
        put(1234543, "csigssd/web/anti-fraud-management");
        put(1236683, "iegsp/procure");
        put(1238589, "l1_engine_grp/Graphics");
        put(1239990, "mmpay_pay_devops/immortal");
        put(1244514, "juliuszhu/CharManager");
        put(1246644, "loganylwu/bubble-use-demo");
        put(1252554, "deer/family_admin");
        put(1256479, "data-platform-front/front-monorepo");
        put(1259493, "westock/stock-union");
        put(1261507, "roboarms/roobarms_web");
        put(1261961, "QCloudCDB-DMC/aid/aid-main");
        put(1266699, "up-club/upclub-api-srv");
        put(1270114, "midas_website/midasbuy_saas_materials");
        put(1272133, "linkzhong/WCode");
        put(1277221, "mmpay_sjt/sjt_ops");
        put(1278032, "strangeli/kol_qp_analyse");
        put(1278377, "mmpay_pay_devops/xdevops");
        put(1286345, "ffe/capital/fund-management");
        put(1295244, "mmec-aop/component/in-context");
        put(1297294, "wxpayfaceiot/Tencent-Palm-Android");
        put(1298770, "TME-pay/TMEPay_Javascript");
        put(1300479, "xy-vip/service/up-action-base-srv");
        put(1301903, "ola/ola-qualityengine/quality-engine-service");
        put(1311689, "mmpay_html/brand_management/marketing/smart-merchant-ops-code");
        put(1322448, "crdc/tuikit/hybrid_uikit");
        put(1323089, "CloudMonitorFrontend/prometheus-app");
        put(1327889, "mpay/creditcard/acquirer/channels");
        put(1327904, "svip/svip_repair_srv");
        put(1328020, "oceanus-poc/changan-streaming-iceberg-jobs");
        put(1333555, "xy-vip/logic/xy-ceiba/tools/scripts");
        put(1341265, "ftp/g_CDG_FIT_PRD_OCenter_OS/soc-web");
        put(1341636, "xdev/xwatt-web/xwatt-web-pro");
        put(1346636, "avr/aigc/ai_cdp_perform");
        put(1348798, "juliuszhu/Ani_Export_Tools");
        put(1351912, "xinlxinli/hybrid_uikit");
        put(1353259, "ffe/op/ops_v2");
        put(1355934, "up-club/upclub-bank-srv");
        put(1356299, "overseasops/goscaffold");
        put(1356302, "overseasops/pyscaffold");
        put(1361135, "jasperdai/hybrid_uikit");
        put(1364257, "yuanfazheng/hybrid_uikit");
        put(1366445, "mileszzhang/hybrid_uikit");
        put(1366783, "mmpay_html/wxpay/user_transaction_list/userroll-manage");
        put(1367737, "majorcai/Work_Automation");
        put(1368061, "icebergfeng/hybrid_uikit");
        put(1368954, "extensions-dev/frontier-rnd_frontier-authentication");
        put(1369967, "extensions/frontier-rnd_frontier-authentication");
        put(1373623, "naturalyuan/hybrid_uikit");
        put(1375802, "mmecadmin/weitilnginxconfweb");
        put(1380007, "frankjlli/work_script");
        put(1385285, "PFDD-AIPT-FE/lily-chat");
        put(1385634, "zackshi/hybrid_uikit");
        put(1391875, "prenenerli/hybrid_uikit");
        put(1394780, "minfra/data_analytics/analytics_api");
        put(1394902, "FuryWeb/h5-fsa");
        put(1395554, "XRD-2.0/SkiaBasedCanvasCore");
        put(1397367, "lexiang/langflow");
        put(1398261, "td/online_rd/dw_design/dwdesigner");
        put(1399548, "ProjectA/community/server/act.buffbuff.com");
        put(1399821, "tyrosning/hybrid_uikit");
        put(1401159, "karaoke/KMP/lib_design");
        put(1403073, "h5-games/phaser-2d-template");
        put(1403321, "cloud-mt/project-standard/plane-app");
        put(1405404, "hysonwu/worthgoing_evaluation_svc");
        put(1406870, "aallenguo/hybrid_uikit");
        put(1407002, "mpay/creditcard/acquirer/acquire-gateway-svr");
        put(1408160, "extensions/huasheng_cursor-rules-huasheng");
        put(1408162, "extensions-dev/huasheng_cursor-rules-huasheng");
        put(1408211, "wenjuli/copygenius");
        put(1409435, "Codifyre/EndlessFire");
        put(1410279, "cassliu/yt-palm-linux-app");
        put(1410284, "h5-games/minigame-server");
        put(1410479, "cloud-mt/project-standard/no1-protal-web");
        put(1411793, "extensions/markshawn2020_oh-my-prompt");
        put(1411796, "extensions-dev/markshawn2020_oh-my-prompt");
        put(1412298, "ProjectA/server/website/gamewtb");
        put(1412624, "ProjectA/server/mysql-listener");
        put(1412807, "pptd_fund_platform/instfund-fee-ao");
        put(1412951, "cloud-mt/chatgpt/bill_analysis");
        put(1413004, "yf/lobe-chat");
        put(1413177, "wxg-bde/wemetisweb");
        put(1413183, "ftp/test/smart_flow");
        put(1413561, "wertyhan/ifbook-ai");
        put(1414386, "cloud-mt/dolphin/pm/plane");
        put(1414440, "cloud-mt/dolphin/special-pm/special-pm-plane");
        put(1415987, "nicejoeli/lingbao");
        put(1416057, "ola/fe/govern");
        put(1416771, "rvec/vodrun");
        put(1417621, "mmpay_pay_devops/wecompay");
        put(1419081, "ams-aigc/admuse-admin");
        put(1420028, "xiaoqhu/Codelet");
        put(1420082, "kobeschen/cursor_demo_project");
        put(1421198, "TME-Interaction-Live-WEB/lens");
        put(1422106, "XRD-2.0/ZhiHui-Canvas-Max");
        put(1422286, "corvoli/hybrid_uikit");
        put(1422419, "IED/KuiklyPolyCity");
        put(1422966, "mmpay-liteapp/wxpay/mch_cashier_app_pay/wxpay-success-page");
        put(1423174, "mmpay_pay_devops/posthog");
        put(1423900, "samleyluo/all-in-one");
        put(1424248, "tmedataleap/myworkchat");
        put(1424492, "extensions-dev/Willbot_tunnelfy");
        put(1424706, "extensions/Willbot_tunnelfy");
        put(1424814, "robot_ai/agents/multi-agent-framework");
        put(1424835, "ssmd-web/server/server-app-cvm-reinstall");
        put(1424853, "ssmd-web/server/server-app-cvm-reboot");
        put(1425167, "qqnews_web/qqnews-platform");
        put(1425425, "camp/lobe-chat");
        put(1425611, "h5-games/games/quiz-runner");
        put(1425723, "msaas/map-msaas-op");
        put(1426454, "susiesmsong/editor-sdk");
        put(1427119, "h5-games/games/happy-crush");
        put(1427150, "ffe/ftp/ftp-docs");
        put(1428208, "mmpay_pay_devops/hcl");
        put(1428653, "lucienduan/cursor-showcase");
        put(1428689, "mmpay_pay_devops/grafana");
        put(1428690, "mmpay_pay_devops/alerting");
        put(1429580, "extensions/utk09-NCL_scss-variables-completion");
        put(1429585, "extensions-dev/utk09-NCL_scss-variables-completion");
        put(1430129, "up-web/x-cms-ai");
        put(1430344, "lct_test_proj/stress/fuauto-stress-webmgr");
        put(1430460, "cadechen/deepsearch");
        put(1430621, "springxiao/patent");
        put(1431582, "frankjwang/fitness-app-flutter");
        put(1431664, "rgzhao/hybrid_uikit");
        put(1432421, "tobinchen/XResearch");
        put(1432943, "APD/fm_platform/allocate_bookkeeping_batch");
        put(1433515, "minfra/Midas_Knowledge");
        put(1434105, "shinxiao/wuji-to-local");
        put(1434187, "chardzhang/hybrid_uikit");
        put(1434960, "BillingSDK/billing_base");
        put(1435019, "CentauriSDK/centauri_pay");
        put(1435281, "henrydwwu/get_answers_for_chat");
        put(1435891, "zjfe/bilibili-data-collection");
        put(1435953, "ProjectA/server/website/buffbuff.com");
        put(1435991, "lucienduan/xdc-operation");
        put(1436377, "APD/task_scheduler/taskview_scheduler_batch");
        put(1436385, "mmpay_nodejs/online_rd/bus_config_mgt");
        put(1436586, "PPRDCenter/littleContract/miniAppDemo");
        put(1436728, "stanleyluo/transferapp");
        put(1436788, "svip/vdesk/knowledge_manage");
        put(1437955, "gerryyang/namesvr");
        put(1438038, "TKD_UG/System1DevGroup/UG3/flow");
        put(1438979, "hysonwu/podcastindexer");
        put(1439017, "maxwellye/idc-share-api");
        put(1439269, "PPRDCenter/AIActivityPay/AIActivityPayService");
        put(1439272, "PPRDCenter/AIActivityPay/AIActivityPayFrontMiniProgram");
        put(1439427, "wefe/jumper");
        put(1439545, "extensions/ScriptEcho_guang-chen-generator-test");
        put(1439553, "extensions-dev/ScriptEcho_guang-chen-generator-test");
        put(1439999, "TKD_UG/System1DevGroup/UG3/mp-video");
        put(1441329, "jackytcui/supplychainweb");
        put(1441436, "tcd-common-support/cpd/cpd-copilot");
        put(1442734, "alexju/xiaowei-assistant-x");
        put(1443641, "td/online_rd/wxp_op_auth_manage/quandun");
        put(1443993, "jimjzhao/zhiyan_predict_model");
        put(1444789, "juliuszhu/ImpureRef");
        put(1445706, "ola/fe/olab");
        put(1446479, "test-ryutang/json_ui");
        put(1446595, "test-ryutang/ai_mindmap");
        put(1446898, "xy-vip/logic/agi/ai-portal");
        put(1447046, "mmpay-xmp/sjt/sjt_receipt/linkqrcode-f2f-payment");
        put(1447155, "dickzhou/mcp");
        put(1447334, "DemoDash/EmotionAI-Companion");
        put(1448187, "chriszwang/inote");
        put(1448632, "tdns-go/tgw-backup-scheduler");
        put(1448643, "cesarezhang/fsm-toy");
        put(1448871, "extensions-dev/CoderOne_aimemory");
        put(1448879, "extensions/CoderOne_aimemory");
        put(1448960, "wxp-mch-prod-fe/marketing/coupon-value");
        put(1449127, "safe_operation_platform/chatroom-miniapp");
        put(1449246, "h5-games/games/blade-storm");
        put(1449363, "mmpay_pay_devops/futurepay");
        put(1449413, "yongweichen/multi-agent-framework-v2");
        put(1449544, "venus-aigc/ComfyUI_frontend");
        put(1449573, "cat/frontend/game/op");
        put(1450533, "miraclekliu/midasbuy-figma");
        put(1450602, "darrenzheng/gitdiagram");
        put(1450605, "EcologicalPlatform/EX_Engine/unreal-mcp");
        put(1450855, "extensions/lonser_Voice-cn-windows");
        put(1450860, "extensions-dev/lonser_Voice-cn-windows");
        put(1450966, "miraclekliu/figma-web");
        put(1451169, "juliuszhu/CharManagerPro");
        put(1451366, "wxg-prc/WeKnowDoc");
        put(1451441, "galileo/cicd-agent");
        put(1451742, "extensions/lonser_Voice-CN-linux");
        put(1451743, "extensions-dev/lonser_Voice-CN-Linux");
        put(1451789, "hysonwu/podcastrecommendation");
        put(1452283, "connorlu/MyD2CTest");
        put(1452510, "groot/groot-robot");
        put(1453173, "skindhu/AI-TASK-MANAGER");
        put(1453516, "zjfe/kol-management-system");
        put(1453517, "v_ruochenxu/oep-system");
        put(1453684, "extensions/magebit_magebit-magento-toolbox");
        put(1453689, "extensions-dev/magebit_magebit-magento-toolbox");
        put(1453743, "ti2.0/ti-devops/ti-vpc-proxy-controller");
        put(1454011, "idc/deliver/idc-deliver-pangu-web");
        put(1454404, "firoyang/SvnAuthorAnalyzer");
        put(1454470, "yaptian/embodied_planning_with_vla_training");
        put(1455153, "linkzhong/wcode_mcp");
        put(1455733, "qb-feeds/NewsAgent/agent_manager_server");
        put(1455805, "PFDD-AIPT-FE/lily-research");
        put(1456458, "timorzheng/rod");
        put(1456560, "msp/ai/cmg-mcp-server");
        put(1456742, "inconhuang/xdc-test");
        put(1456830, "aikewang/DDoS-Specialist-Mcp");
        put(1456875, "fishcui/prototype");
        put(1457266, "shuaihuayan/AICodingLibrary");
        put(1457292, "fcd/fit-one/tool/ftp-vscode-plugin");
        put(1457421, "ngr_backend_group/toolkit_frontend");
        put(1457547, "IED/Daoju_Web");
        put(1457790, "abwu/node-mcp");
        put(1457905, "lemonding/my_extensions");
        put(1458064, "extensions-dev/swiftlydaniel_oklch-color-visualiser");
        put(1458069, "extensions/swiftlydaniel_oklch-color-visualiser");
        put(1458197, "questguo/tica-standard-flutter");
        put(1458860, "sec_platform/xj.woa.com");
        put(1459336, "chenyuwang/py2lua");
        put(1459836, "alawnxu/browserbase-app");
        put(1459904, "timorzheng/figma2code-android-demo");
        put(1460078, "up_rd1/AILab/zhiji_rules");
        put(1460162, "jasonsjiang/sim-switcher");
        put(1460258, "stevelliu/tech4good_tools");
        put(1460361, "HRMove/er-refactor/ehr-refactor-service");
        put(1460425, "sec-zsd/ZsdChat");
        put(1460504, "juliuszhu/Environment_Pipeline_Tools");
        put(1460692, "piboyeliu/novel-video");
        put(1460866, "chriskzhou/highlight");
        put(1461461, "APD/cft_funds_mgr/funds_analyse/cfm_funds_analyse_batch");
        put(1461527, "blandozhang/langfuse");
        put(1461600, "zjfe/server/open-data-node-server");
        put(1461688, "ffe/common/cursor-ai-cli");
        put(1461727, "csig_datacenter/go/mcp-clickhouse");
        put(1461915, "martianliu/learn");
        put(1462293, "mur-survey/survey-e2e-py");
        put(1462369, "gCDGFITCFPDRDCENTPAY/boss/entpay-boss-api");
        put(1462480, "PFDD-AIPT-FE/llm-deepresearch");
        put(1462616, "juliuszhu/TimePilot");
        put(1462691, "mmpay_html/wxpay/user_transaction_list/userbill-manager");
        put(1463157, "linkzhong/clangd-vscode");
        put(1463512, "nz_frontend/clsq-h5");
        put(1464101, "abwu/ddd-mcp-app");
        put(1464186, "camp/camp-ci-worker");
        put(1464206, "leeonchen/boss_merchant_frontend");
        put(1464631, "extensions-dev/iganbold_yoyo-ai-versioning");
        put(1464665, "extensions/texra-ai_texra");
        put(1464666, "extensions/iganbold_yoyo-ai-versioning");
        put(1465012, "iegsp/frontend/supplier-video");
        put(1465320, "jonescai/antdx-demo");
        put(1465450, "blueking-plugins/saas/tapd-move-itera");
        put(1465510, "juliuszhu/StorageMap");
        put(1465734, "stannhuang/incentive-batch-import-formatter");
        put(1466074, "hunyuan-open-platform/comfyui-services/ComfyUI_frontend");
        put(1466418, "sec-zsd/DDoS-Specialist-Mcp");
        put(1466440, "tianyi/project-man");
        put(1466468, "ads/ppfx/algo-fe");
        put(1466482, "camp_overseas/comfyui-scheduler");
        put(1466875, "majorcai/mvpsvntest");
        put(1467143, "cheowang/ns3-test");
        put(1467147, "abwu/lily-sdk");
        put(1467237, "iegg_distribution/Bot/infini-bot-frontend");
        put(1467809, "extensions/byterover_byterover");
        put(1467813, "extensions-dev/byterover_byterover");
        put(1467918, "xinpeiliao/panda_sql");
        put(1467941, "paytonsun/k8helper");
        put(1468035, "iegg_distribution/Bot/infini-bot-srv");
        put(1468313, "winnyli/PxTemplate");
        put(1468391, "zombieyang/LLM_BT_DEMO");
        put(1468483, "chriskzhou/idl-rules");
        put(1468647, "chriskzhou/yuanbao-flow");
        put(1468672, "gdp/innovation/langfuse");
        put(1468699, "blueking-plugins/saas/tapd-itera-lock");
        put(1468879, "rickiewang/dingkai-demo01");
        put(1468991, "rickiewang/dingkai-asp-demo03");
        put(1469209, "zekunzhan/demo-livecrm");
        put(1469369, "zachxia/movie-search");
        put(1469711, "natelin/midas_mcp_hub");
        put(1469729, "iegg_distribution/Bot/InfiniBot-cursor-config");
        put(1469823, "jackwlchen/ai-monitor");
        put(1469908, "extensions/VolarTools_volar-ai");
        put(1469910, "extensions-dev/VolarTools_volar-ai");
        put(1470266, "gpts/ai-components/webhook-manager");
        put(1470317, "alice/super_admin");
        put(1470321, "natelin/midas-mcp-gateway");
        put(1470364, "g_WXG_OB_MP_D14/ai-industry-analytics");
        put(1470432, "zjfe/open-data-web");
        put(1470452, "blueking-plugins/saas/update-iteration");
        put(1470506, "kazenzhong/posing-ai-proto");
        put(1470963, "CloudBase/cloudbase-turbo-delploy");
        put(1471084, "cat/frontend/game/tanks-infinity");
        put(1471181, "gpc-front-end/aix-mcp-gateway");
        put(1471267, "ams-aigc/ams_aigc_fe");
        put(1471870, "lucaske/tmp-ai");
        put(1471962, "blueking-plugins/saas/get-tapd-user");
        put(1472292, "dreamshop/app/vcmsnext");
        put(1472432, "kimcao/cloud-mcp-server");
        put(1472815, "wymanwei/knowledgeWorkShop");
        put(1473186, "talentzhang/rag-test");
        put(1473238, "lindalli/hybrid_uikit");
        put(1473630, "wx_credit_card/ai/openaiAPI2fitllmAPI");
        put(1474053, "GameHelperTeam/official_content_lib");
        put(1487638, "csigssd/web/microapp-aigc-platform");
        put(1488016, "td/online_rd/fron_interactive_low_code_dev/xpage-liteapp-templates");
        put(1488042, "noahzuo/project_janus");
        put(1488298, "gaccobzhang/mch-payment-product");
        put(1488798, "rig/tmp-mcp-agent");
        put(1489599, "stevezyyang/gold-coin");
        put(1490107, "currychen/husky-lock-check");
        put(1490524, "blueking-plugins/saas/update-smart-tab");
        put(1491171, "rainmenxia/mch-apply");
        put(1491196, "Agents/external/browser-use");
        put(1491321, "extensions/BrainGridAI_braingrid");
        put(1491657, "eddyzheng/OpenManus");
        put(1491874, "kuangao/shell-scripts");
        put(1492080, "wertyhan/ifbook-ariadne");
        put(1492106, "piboyeliu/sjs");
        put(1492354, "dataGPT/knowledgeWorkShop");
        put(1492887, "extensions/byterover_rover");
        put(1492890, "extensions-dev/byterover_rover");
        put(1492945, "rovyluo/ai-tdesign");
        put(1493713, "aveszhang/cursortest");
        put(1493976, "ericzyma/MeshSeriesGen");
        put(1494594, "yanyilai/label-studio");
        put(1494686, "yuriqi/viewcomfy");
        put(1494693, "codebuddy/knot");

    }};

    public static void main(String[] args) {
        try {
            // 1. 抽取JSON数据
            ExtractedData extractedData1 = extractDataFromJson(new File("/Users/wangpei/Downloads/配置过cursor的项目修改配置原始记录.json"));
            ExtractedData extractedData2 = extractDataFromJson(new File("/Users/wangpei/Downloads/配置过cursor的项目配置记录2.json"));
            List<Map<String, String>> data = new ArrayList<>();
            data.addAll(extractedData1.data);
            data.addAll(extractedData2.data);

            System.out.println("===================\n=============");
            System.out.println(data);
            List<Map<String, String>> dataNew = merge(data);

            List<String> headers = new ArrayList<>();
            headers.add("projId");
            headers.add("projectPath");
            headers.add("projectType");
            headers.add("option");
            headers.add("branch");
            headers.add("fullPath");
            headers.add("fullPathUrl");
            ExtractedData extractedData = new ExtractedData(headers, dataNew);
            // 2. 将数据写入Excel
            writeDataToExcel(extractedData, "/Users/wangpei/Downloads/output.xlsx");

            System.out.println("转换成功！共处理 " + extractedData.data.size() + " 条记录");
        } catch (Exception e) {
            System.err.println("处理过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<Map<String, String>> merge(List<Map<String, String>> data) {

        Map<String, Map<String, String>> resultMap = new HashMap<>();
        for (Map<String, String> m : data) {
            String key = m.get("projId") + "_" + m.get("fullPath");
            if (!resultMap.containsKey(key)) {
                resultMap.put(key, m);
            } else {
                if (Objects.equals(m.get("branch"), "master") || Objects.equals(m.get("branch"), "main")) {
                    resultMap.put(key, m);
                }
            }
        }

        System.out.println("====================\n===============");
        for (Map<String, String> m : resultMap.values()) {
            System.out.println(m);
        }
        return new ArrayList<>(resultMap.values());
    }

    /**
     * 从JSON文件中提取数据
     *
     * @param jsonFile JSON文件对象
     * @return 包含表头和数据的结构体
     * @throws Exception 文件处理异常
     */
    private static ExtractedData extractDataFromJson(File jsonFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        Set<String> headers = new LinkedHashSet<>(); // 使用LinkedHashSet保持顺序并去重
        List<Map<String, String>> data = new ArrayList<>();

        JsonNode hits = rootNode.get("hits").get("hits");
        if (hits.isArray()) {
            // 第一遍: 收集所有可能的表头
//            for (JsonNode node : hits) {
//                Iterator<String> fieldNames = node.fieldNames();
//                while (fieldNames.hasNext()) {
//                    headers.add(fieldNames.next());
//                }
//            }

            // 第二遍: 提取数据并保持顺序
            for (JsonNode node : hits) {
                try {

                    JsonNode sourceNode = node.get("_source");
                    String _type = node.get("_type").asText();
                    String projectType = Objects.equals("svn_file_diff_loc", _type) ? "svn" : "git";
                    if (sourceNode.get("repoType") != null) {
                        projectType = sourceNode.get("repoType").asText();
                    }

                    String fullPath = "";
                    if (sourceNode.get("fullPath") != null) {
                        fullPath = sourceNode.get("fullPath").asText();
                    } else {
                        fullPath = sourceNode.get("path").asText();
                    }
                    Integer projId = 0;
                    if (sourceNode.get("projId") != null) {
                        projId = sourceNode.get("projId").asInt();
                    } else {
                        projId = sourceNode.get("projectId").asInt();
                    }
                    String option = "";
                    if (sourceNode.get("option") != null) {
                        option = sourceNode.get("option").asText();
                        if (Objects.equals("D", option)) {
                            continue;
                        }
                    }
                    JsonNode branchesNode = sourceNode.get("branches");
                    String branch = "";
                    if (branchesNode != null && !branchesNode.isEmpty()) {

                        for (JsonNode bn : branchesNode) {
                            if (Objects.equals(bn.get("branchName").asText(), "refs/heads/master")) {
                                branch = "refs/heads/master";
                                break;
                            }
                        }
                        if (Objects.equals("", branch)) {
                            branch = branchesNode.get(0).get("branchName").asText();
                        }
                        branch = branch.replaceAll("refs/heads/", "");
                    } else if (Objects.equals("git", projectType)) {
                        branch = "master";
                    }

                    Map<String, String> rowMap = new LinkedHashMap<>();
                    rowMap.put("projId", String.valueOf(projId));
                    rowMap.put("projectType", projectType);
                    rowMap.put("option", option);
                    rowMap.put("branch", branch);
                    rowMap.put("fullPath", fullPath);

                    String projectPath = projectMap.get(projId);
                    rowMap.put("projectPath", projectPath);

                    String fullPathUrl = "";
                    if(projId==1466875){
                        System.out.println("xxx");
                    }
                    if (Objects.equals("svn", projectType)) {
                        fullPathUrl = "https://git.woa.com/" + projectPath + "/blob/HEAD" + fullPath;
                    } else {
                        fullPathUrl = "https://git.woa.com/" + projectPath + "/blob/" + branch + (fullPath.startsWith("/") ? "" : "/") + fullPath;
                    }
                    rowMap.put("fullPathUrl", fullPathUrl);
//                    System.out.println(rowMap);
                    data.add(rowMap);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        } else {
            throw new IllegalArgumentException("JSON结构不是对象数组");
        }

        // 转换为列表保持顺序
        List<String> orderedHeaders = new ArrayList<>(headers);
        return new ExtractedData(orderedHeaders, data);
    }

    /**
     * 将数据写入Excel文件
     *
     * @param extractedData 包含表头和数据的结构体
     * @param outputFilePath 输出Excel文件路径
     * @throws Exception 文件操作异常
     */
    private static void writeDataToExcel(ExtractedData extractedData,
            String outputFilePath) throws Exception {
        List<String> headers = extractedData.headers;
        List<Map<String, String>> data = extractedData.data;

        try (Workbook workbook = new XSSFWorkbook();
                FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

            Sheet sheet = workbook.createSheet("JSON Data");

            // 创建表头行
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = createHeaderStyle(workbook);

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
            }

            // 填充数据行
            for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                Map<String, String> rowData = data.get(rowIndex);

                for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                    String value = rowData.get(headers.get(colIndex));
                    row.createCell(colIndex).setCellValue(value);
                }
            }

            // 自适应列宽
            autoSizeColumns(sheet, headers.size());

            workbook.write(outputStream);
        }
    }

    /**
     * 创建表头样式
     *
     * @param workbook Excel工作簿
     * @return 表头单元格样式
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    /**
     * 自动调整列宽
     *
     * @param sheet Excel工作表
     * @param columnCount 列数
     */
    private static void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * 数据容器类，用于封装提取的数据
     */
    static class ExtractedData {

        List<String> headers;
        List<Map<String, String>> data;

        public ExtractedData(List<String> headers, List<Map<String, String>> data) {
            this.headers = headers;
            this.data = data;
        }
    }
}
