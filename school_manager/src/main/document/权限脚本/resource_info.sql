/** 1.商品管理 */
/** 分类列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (1, '商品分类列表', '/good/goodType/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '分类列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (2, '商品分类详情', '/good/goodType/detail.do', (select ri.id from resource_info ri where ri.res_url='/good/goodType/list.do'),(select mi.id from menu_info mi where mi.resource_name = '分类列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (3, '商品分类编辑', '/good/goodType/edit.do', (select ri.id from resource_info ri where ri.res_url='/good/goodType/list.do'),(select mi.id from menu_info mi where mi.resource_name = '分类列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (4, '商品分类保存', '/good/goodType/save.do', (select ri.id from resource_info ri where ri.res_url='/good/goodType/list.do'),(select mi.id from menu_info mi where mi.resource_name = '分类列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (5, '商品分类删除', '/good/goodType/del.do', (select ri.id from resource_info ri where ri.res_url='/good/goodType/list.do'),(select mi.id from menu_info mi where mi.resource_name = '分类列表'),NULL);

/** 品牌列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (10, '商品品牌列表', '/good/goodBrand/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '品牌列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (11, '商品品牌详情', '/good/goodBrand/detail.do', (select ri.id from resource_info ri where ri.res_url='/good/goodBrand/list.do'),(select mi.id from menu_info mi where mi.resource_name = '品牌列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (12, '商品品牌编辑', '/good/goodBrand/edit.do', (select ri.id from resource_info ri where ri.res_url='/good/goodBrand/list.do'),(select mi.id from menu_info mi where mi.resource_name = '品牌列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (13, '商品品牌保存', '/good/goodBrand/save.do', (select ri.id from resource_info ri where ri.res_url='/good/goodBrand/list.do'),(select mi.id from menu_info mi where mi.resource_name = '品牌列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (14, '商品品牌删除', '/good/goodBrand/del.do', (select ri.id from resource_info ri where ri.res_url='/good/goodBrand/list.do'),(select mi.id from menu_info mi where mi.resource_name = '品牌列表'),NULL);

/** 商品列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (20, '商品列表', '/good/goodInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (21, '商品详情', '/good/goodInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (22, '商品编辑', '/good/goodInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (23, '商品保存', '/good/goodInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (24, '商品删除', '/good/goodInfo/del.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (25, '商品上架/下架', '/good/goodInfo/upAndDown.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (26, '商品批量上架/下架', '/good/goodInfo/upAndDownAll.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (27, '商品导出数据', '/good/goodInfo/exportData.do', (select ri.id from resource_info ri where ri.res_url='/good/goodInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品列表'),NULL);

/** 商品审核 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (40, '商品审核列表', '/good/goodAuth/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '商品审核列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (41, '商品审核详情', '/good/goodAuth/detail.do', (select ri.id from resource_info ri where ri.res_url='/good/goodAuth/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品审核列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (42, '商品审核', '/good/goodAuth/auth.do', (select ri.id from resource_info ri where ri.res_url='/good/goodAuth/list.do'),(select mi.id from menu_info mi where mi.resource_name = '商品审核列表'),NULL);

/** 2.订单管理 */
/** 订单列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (50, '订单列表', '/order/order/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '订单列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (51, '订单详情', '/order/order/detail.do', (select ri.id from resource_info ri where ri.res_url='/order/order/list.do'),(select mi.id from menu_info mi where mi.resource_name = '订单列表'),NULL);

/** 退货单列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (70, '退货单列表', '/order/refund/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '退货单列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (71, '退货单详情', '/order/refund/detail.do', (select ri.id from resource_info ri where ri.res_url='/order/refund/list.do'),(select mi.id from menu_info mi where mi.resource_name = '退货单列表'),NULL);

/** 换货单列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (90, '换货单列表', '/order/exchange/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '换货单列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (91, '换货单详情', '/order/exchange/detail.do', (select ri.id from resource_info ri where ri.res_url='/order/exchange/list.do'),(select mi.id from menu_info mi where mi.resource_name = '换货单列表'),NULL);

/** 物流列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (110, '物流列表', '/order/logistics/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '物流列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (111, '物流详情', '/order/logistics/detail.do', (select ri.id from resource_info ri where ri.res_url='/order/logistics/list.do'),(select mi.id from menu_info mi where mi.resource_name = '物流列表'),NULL);

/** 3.会员管理 */
/** 会员列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (130, '会员列表', '/user/userInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (131, '会员详情', '/user/userInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (132, '会员编辑', '/user/userInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (133, '会员保存', '/user/userInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (134, '加入黑名单', '/user/userInfo/addBlack.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (135, '会员禁用', '/user/userInfo/disable.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (136, '会员启用', '/user/userInfo/enable.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (137, '会员赠送积分', '/user/userInfo/toAddScore.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (138, '会员积分保存', '/user/userInfo/addScore.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (139, '会员类型切换', '/user/userInfo/toChangeType.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (140, '会员类型保存', '/user/userInfo/changeType.do', (select ri.id from resource_info ri where ri.res_url='/user/userInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '会员列表'),NULL);

/** 会员黑名单列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (160, '黑名单列表', '/user/black/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '黑名单列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (161, '黑名单详情', '/user/black/detail.do', (select ri.id from resource_info ri where ri.res_url='/user/black/list.do'),(select mi.id from menu_info mi where mi.resource_name = '黑名单列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (162, '黑名单启用', '/user/black/enable.do', (select ri.id from resource_info ri where ri.res_url='/user/black/list.do'),(select mi.id from menu_info mi where mi.resource_name = '黑名单列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (163, '黑名单禁用', '/user/black/disable.do', (select ri.id from resource_info ri where ri.res_url='/user/black/list.do'),(select mi.id from menu_info mi where mi.resource_name = '黑名单列表'),NULL);

/** 积分类型列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (180, '积分类型列表', '/user/userPointsInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '积分类型列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (181, '积分类型详情', '/user/userPointsInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/user/userPointsInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '积分类型列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (182, '积分类型编辑', '/user/userPointsInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/user/userPointsInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '积分类型列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (183, '积分类型保存', '/user/userPointsInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/user/userPointsInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '积分类型列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (184, '积分类型删除', '/user/userPointsInfo/del.do', (select ri.id from resource_info ri where ri.res_url='/user/userPointsInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '积分类型列表'),NULL);

/** 4.分析统计 */
/** 用户统计 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (200, '用户统计', '/statistics/user/statistics.do', 0,(select mi.id from menu_info mi where mi.resource_name = '用户统计'),NULL);
/** 商品销售统计 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (220, '商品销售统计', '/statistics/goodSale/statistics.do', 0,(select mi.id from menu_info mi where mi.resource_name = '商品销售统计'),NULL);
/** 充值统计 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (240, '充值统计', '/statistics/recharge/statistics.do', 0,(select mi.id from menu_info mi where mi.resource_name = '充值统计'),NULL);
/** 订单统计 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (260, '订单统计', '/statistics/order/statistics.do', 0,(select mi.id from menu_info mi where mi.resource_name = '订单统计'),NULL);
/** 积分统计 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (280, '积分统计', '/statistics/point/statistics.do', 0,(select mi.id from menu_info mi where mi.resource_name = '积分统计'),NULL);

/** 5.充值管理 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (300, '充值列表', '/recharge/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '充值管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (301, '充值详情', '/recharge/detail.do', (select ri.id from resource_info ri where ri.res_url='/recharge/list.do'),(select mi.id from menu_info mi where mi.resource_name = '充值管理'),NULL);

/** 6.内容管理 */
/** banner */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (320, 'banner列表', '/content/banner/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (321, 'banner详情', '/content/banner/detail.do', (select ri.id from resource_info ri where ri.res_url='/content/banner/list.do'),(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (322, 'banner编辑', '/content/banner/edit.do', (select ri.id from resource_info ri where ri.res_url='/content/banner/list.do'),(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (323, 'banner保存', '/content/banner/save.do', (select ri.id from resource_info ri where ri.res_url='/content/banner/list.do'),(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (324, 'banner删除', '/content/banner/del.do', (select ri.id from resource_info ri where ri.res_url='/content/banner/list.do'),(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (325, 'banner显示', '/content/banner/show.do', (select ri.id from resource_info ri where ri.res_url='/content/banner/list.do'),(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (326, 'banner隐藏', '/content/banner/hide.do', (select ri.id from resource_info ri where ri.res_url='/content/banner/list.do'),(select mi.id from menu_info mi where mi.resource_name = 'banner'),NULL);

/** 常见问题 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (340, '常见问题列表', '/content/question/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '常见问题'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (341, '常见问题详情', '/content/question/detail.do', (select ri.id from resource_info ri where ri.res_url='/content/question/list.do'),(select mi.id from menu_info mi where mi.resource_name = '常见问题'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (342, '常见问题编辑', '/content/question/toAnswer.do', (select ri.id from resource_info ri where ri.res_url='/content/question/list.do'),(select mi.id from menu_info mi where mi.resource_name = '常见问题'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (343, '常见问题保存', '/content/question/answer.do', (select ri.id from resource_info ri where ri.res_url='/content/question/list.do'),(select mi.id from menu_info mi where mi.resource_name = '常见问题'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (344, '常见问题删除', '/content/question/del.do', (select ri.id from resource_info ri where ri.res_url='/content/question/list.do'),(select mi.id from menu_info mi where mi.resource_name = '常见问题'),NULL);

/** 公告报道 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (360, '公告报道列表', '/content/notice/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (361, '公告报道详情', '/content/notice/detail.do', (select ri.id from resource_info ri where ri.res_url='/content/notice/list.do'),(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (362, '公告报道编辑', '/content/notice/edit.do', (select ri.id from resource_info ri where ri.res_url='/content/notice/list.do'),(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (363, '公告报道保存', '/content/notice/save.do', (select ri.id from resource_info ri where ri.res_url='/content/notice/list.do'),(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (364, '公告报道删除', '/content/notice/del.do', (select ri.id from resource_info ri where ri.res_url='/content/notice/list.do'),(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (365, '公告报道显示', '/content/notice/show.do', (select ri.id from resource_info ri where ri.res_url='/content/notice/list.do'),(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (366, '公告报道隐藏', '/content/notice/hide.do', (select ri.id from resource_info ri where ri.res_url='/content/notice/list.do'),(select mi.id from menu_info mi where mi.resource_name = '公告报道'),NULL);

/** 7.app管理 */
/** 栏目列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (380, '栏目列表', '/content/column/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '栏目列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (381, '栏目详情', '/content/column/detail.do', (select ri.id from resource_info ri where ri.res_url='/content/column/list.do'),(select mi.id from menu_info mi where mi.resource_name = '栏目列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (382, '栏目编辑', '/content/column/edit.do', (select ri.id from resource_info ri where ri.res_url='/content/column/list.do'),(select mi.id from menu_info mi where mi.resource_name = '栏目列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (383, '栏目保存', '/content/column/save.do', (select ri.id from resource_info ri where ri.res_url='/content/column/list.do'),(select mi.id from menu_info mi where mi.resource_name = '栏目列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (384, '栏目删除', '/content/column/del.do', (select ri.id from resource_info ri where ri.res_url='/content/column/list.do'),(select mi.id from menu_info mi where mi.resource_name = '栏目列表'),NULL);

/** 版本列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (400, '版本列表', '/app/appVersionInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (401, '版本详情', '/app/appVersionInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/app/appVersionInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (402, '版本编辑', '/app/appVersionInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/app/appVersionInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (403, '版本保存', '/app/appVersionInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/app/appVersionInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (404, '版本删除', '/app/appVersionInfo/del.do', (select ri.id from resource_info ri where ri.res_url='/app/appVersionInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (405, '版本强制', '/app/appVersionInfo/isForce.do', (select ri.id from resource_info ri where ri.res_url='/app/appVersionInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (406, '版本非强制', '/app/appVersionInfo/cacleForce.do', (select ri.id from resource_info ri where ri.res_url='/app/appVersionInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '版本列表'),NULL);

/** 8.权限管理 */
/** 用户列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (420, '用户列表', '/permission/adminUser/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '用户列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (421, '用户详情', '/permission/adminUser/detail.do', (select ri.id from resource_info ri where ri.res_url='/permission/adminUser/list.do'),(select mi.id from menu_info mi where mi.resource_name = '用户列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (422, '用户编辑', '/permission/adminUser/edit.do', (select ri.id from resource_info ri where ri.res_url='/permission/adminUser/list.do'),(select mi.id from menu_info mi where mi.resource_name = '用户列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (423, '用户保存', '/permission/adminUser/save.do', (select ri.id from resource_info ri where ri.res_url='/permission/adminUser/list.do'),(select mi.id from menu_info mi where mi.resource_name = '用户列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (424, '用户删除', '/permission/adminUser/del.do', (select ri.id from resource_info ri where ri.res_url='/permission/adminUser/list.do'),(select mi.id from menu_info mi where mi.resource_name = '用户列表'),NULL);

/** 角色列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (440, '角色列表', '/permission/role/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '角色列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (441, '角色详情', '/permission/role/detail.do', (select ri.id from resource_info ri where ri.res_url='/permission/role/list.do'),(select mi.id from menu_info mi where mi.resource_name = '角色列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (442, '角色编辑', '/permission/role/edit.do', (select ri.id from resource_info ri where ri.res_url='/permission/role/list.do'),(select mi.id from menu_info mi where mi.resource_name = '角色列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (443, '角色保存', '/permission/role/save.do', (select ri.id from resource_info ri where ri.res_url='/permission/role/list.do'),(select mi.id from menu_info mi where mi.resource_name = '角色列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (444, '角色删除', '/permission/role/del.do', (select ri.id from resource_info ri where ri.res_url='/permission/role/list.do'),(select mi.id from menu_info mi where mi.resource_name = '角色列表'),NULL);

/** 权限列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (460, '权限列表', '/permission/resourceInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '权限列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (461, '权限详情', '/permission/resourceInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/permission/resourceInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '权限列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (462, '权限编辑', '/permission/resourceInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/permission/resourceInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '权限列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (463, '权限保存', '/permission/resourceInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/permission/resourceInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '权限列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (464, '权限删除', '/permission/resourceInfo/del.do', (select ri.id from resource_info ri where ri.res_url='/permission/resourceInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '权限列表'),NULL);

/** 9.代理商管理 */
/** 代理商列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (480, '代理商列表', '/user/agent/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '代理商列表'),NULL);
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (481, '代理商详情', '/user/agent/detail.do', (select ri.id from resource_info ri where ri.res_url='/proxy/proxy/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (482, '代理商编辑', '/user/agent/edit.do', (select ri.id from resource_info ri where ri.res_url='/proxy/proxy/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (483, '代理商保存', '/user/agent/save.do', (select ri.id from resource_info ri where ri.res_url='/proxy/proxy/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (484, '代理商删除', '/user/agent/del.do', (select ri.id from resource_info ri where ri.res_url='/proxy/proxy/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商列表'),NULL);*/

/** 下属经销商列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (500, '下属经销商列表', '/user/dealer/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '下属经销商列表'),NULL);
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (501, '下属经销商详情', '/proxy/merchant/detail.do', (select ri.id from resource_info ri where ri.res_url='/proxy/merchant/list.do'),(select mi.id from menu_info mi where mi.resource_name = '下属经销商列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (502, '下属经销商编辑', '/proxy/merchant/edit.do', (select ri.id from resource_info ri where ri.res_url='/proxy/merchant/list.do'),(select mi.id from menu_info mi where mi.resource_name = '下属经销商列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (503, '下属经销商保存', '/proxy/merchant/save.do', (select ri.id from resource_info ri where ri.res_url='/proxy/merchant/list.do'),(select mi.id from menu_info mi where mi.resource_name = '下属经销商列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (504, '下属经销商删除', '/proxy/merchant/del.do', (select ri.id from resource_info ri where ri.res_url='/proxy/merchant/list.do'),(select mi.id from menu_info mi where mi.resource_name = '下属经销商列表'),NULL);*/

/** 代理商个人列表 */
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (520, '代理商个人列表', '/proxy/person/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '代理商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (521, '代理商个人详情', '/proxy/person/detail.do', (select ri.id from resource_info ri where ri.res_url='/proxy/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (522, '代理商个人编辑', '/proxy/person/edit.do', (select ri.id from resource_info ri where ri.res_url='/proxy/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (523, '代理商个人保存', '/proxy/person/save.do', (select ri.id from resource_info ri where ri.res_url='/proxy/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (524, '代理商个人删除', '/proxy/person/del.do', (select ri.id from resource_info ri where ri.res_url='/proxy/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '代理商个人列表'),NULL);*/

/** 10.经销商管理 */
/** 经销商列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (600, '经销商列表', '/merchant/merchantInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '经销商列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (601, '经销商详情', '/merchant/merchantInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/merchant/merchantInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (602, '经销商编辑', '/merchant/merchantInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/merchant/merchantInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (603, '经销商保存', '/merchant/merchantInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/merchant/merchantInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (604, '经销商删除', '/merchant/merchantInfo/del.do', (select ri.id from resource_info ri where ri.res_url='/merchant/merchantInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商列表'),NULL);

/** 经销商个人列表 */
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (620, '经销商个人列表', '/merchant/person/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '经销商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (621, '经销商个人详情', '/merchant/person/detail.do', (select ri.id from resource_info ri where ri.res_url='/merchant/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (622, '经销商个人编辑', '/merchant/person/edit.do', (select ri.id from resource_info ri where ri.res_url='/merchant/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (623, '经销商个人保存', '/merchant/person/save.do', (select ri.id from resource_info ri where ri.res_url='/merchant/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商个人列表'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (624, '经销商个人删除', '/merchant/person/del.do', (select ri.id from resource_info ri where ri.res_url='/merchant/person/list.do'),(select mi.id from menu_info mi where mi.resource_name = '经销商个人列表'),NULL);*/

/** 短信管理 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (640, '短信列表', '/shortMessage/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '短信管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (641, '短信详情', '/shortMessage/detail.do', (select ri.id from resource_info ri where ri.res_url='/shortMessage/list.do'),(select mi.id from menu_info mi where mi.resource_name = '短信管理'),NULL);
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (642, '短信编辑', '/oldDoctor/edit.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (643, '短信保存', '/oldDoctor/save.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (644, '短信删除', '/oldDoctor/del.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/

/** 老中医问诊管理 */
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (380, '老中医问诊列表', '/oldDoctor/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (381, '老中医问诊详情', '/oldDoctor/detail.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (382, '老中医问诊编辑', '/oldDoctor/edit.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (383, '老中医问诊保存', '/oldDoctor/save.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (384, '老中医问诊删除', '/oldDoctor/del.do', (select ri.id from resource_info ri where ri.res_url='/oldDoctor/list.do'),(select mi.id from menu_info mi where mi.resource_name = '老中医问诊管理'),NULL);*/

/** 金融贷款管理 */
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (400, '金融贷款列表', '/loan/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '金融贷款管理'),NULL);
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (401, '金融贷款详情', '/loan/detail.do', (select ri.id from resource_info ri where ri.res_url='/loan/list.do'),(select mi.id from menu_info mi where mi.resource_name = '金融贷款管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (402, '金融贷款编辑', '/loan/edit.do', (select ri.id from resource_info ri where ri.res_url='/loan/list.do'),(select mi.id from menu_info mi where mi.resource_name = '金融贷款管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (403, '金融贷款保存', '/loan/save.do', (select ri.id from resource_info ri where ri.res_url='/loan/list.do'),(select mi.id from menu_info mi where mi.resource_name = '金融贷款管理'),NULL);*/
/**insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (404, '金融贷款删除', '/loan/del.do', (select ri.id from resource_info ri where ri.res_url='/loan/list.do'),(select mi.id from menu_info mi where mi.resource_name = '金融贷款管理'),NULL);*/

