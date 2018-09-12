/** 1.快递管理 */
/** 收件列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (1, '收件列表', '/express/expressReceive/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '收件列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (2, '收件详情', '/express/expressReceive/detail.do', (select ri.id from resource_info ri where ri.res_url='/express/expressReceive/list.do'),(select mi.id from menu_info mi where mi.resource_name = '收件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (3, '收件编辑', '/express/expressReceive/edit.do', (select ri.id from resource_info ri where ri.res_url='/express/expressReceive/list.do'),(select mi.id from menu_info mi where mi.resource_name = '收件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (4, '收件保存', '/express/expressReceive/save.do', (select ri.id from resource_info ri where ri.res_url='/express/expressReceive/list.do'),(select mi.id from menu_info mi where mi.resource_name = '收件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (5, '收件删除', '/express/expressReceive/del.do', (select ri.id from resource_info ri where ri.res_url='/express/expressReceive/list.do'),(select mi.id from menu_info mi where mi.resource_name = '收件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (6, '补单', '/express/expressReceive/reOrder.do', (select ri.id from resource_info ri where ri.res_url='/express/expressReceive/list.do'),(select mi.id from menu_info mi where mi.resource_name = '收件列表'),NULL);

/** 寄件列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (31, '寄件列表', '/express/expressSend/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (32, '寄件详情', '/express/expressSend/detail.do', (select ri.id from resource_info ri where ri.res_url='/express/expressSend/list.do'),(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (33, '寄件编辑', '/express/expressSend/edit.do', (select ri.id from resource_info ri where ri.res_url='/express/expressSend/list.do'),(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (34, '寄件保存', '/express/expressSend/save.do', (select ri.id from resource_info ri where ri.res_url='/express/expressSend/list.do'),(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (35, '寄件删除', '/express/expressSend/del.do', (select ri.id from resource_info ri where ri.res_url='/express/expressSend/list.do'),(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (36, '补单', '/express/expressSend/reOrder.do', (select ri.id from resource_info ri where ri.res_url='/express/expressSend/list.do'),(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (37, '退款', '/express/expressSend/refund.do', (select ri.id from resource_info ri where ri.res_url='/express/expressSend/list.do'),(select mi.id from menu_info mi where mi.resource_name = '寄件列表'),NULL);

/** 快递柜取件信息 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (61, '柜子取件列表', '/expressBoxInfo/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '柜子取件列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (62, '柜子取件详情', '/expressBoxInfo/detail.do', (select ri.id from resource_info ri where ri.res_url='/expressBoxInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '柜子取件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (63, '柜子取件编辑', '/expressBoxInfo/edit.do', (select ri.id from resource_info ri where ri.res_url='/expressBoxInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '柜子取件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (64, '柜子取件保存', '/expressBoxInfo/save.do', (select ri.id from resource_info ri where ri.res_url='/expressBoxInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '柜子取件列表'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (65, '柜子取件删除', '/expressBoxInfo/del.do', (select ri.id from resource_info ri where ri.res_url='/expressBoxInfo/list.do'),(select mi.id from menu_info mi where mi.resource_name = '柜子取件列表'),NULL);


/** 2.订单管理 */
/** 支付订单列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (101, '支付订单列表', '/order/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '支付订单列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (102, '支付订单详情', '/order/detail.do', (select ri.id from resource_info ri where ri.res_url='/order/list.do'),(select mi.id from menu_info mi where mi.resource_name = '支付订单列表'),NULL);

/** 退款订单列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (151, '退款订单列表', '/refundOrder/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '退款订单列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (152, '退款订单详情', '/refundOrder/detail.do', (select ri.id from resource_info ri where ri.res_url='/refundOrder/list.do'),(select mi.id from menu_info mi where mi.resource_name = '退款订单列表'),NULL);


/** 3.快递公司管理 */
/** 快递公司列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (201, '快递公司列表', '/expressCompany/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '快递公司管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (202, '快递公司详情', '/expressCompany/detail.do', (select ri.id from resource_info ri where ri.res_url='/expressCompany/list.do'),(select mi.id from menu_info mi where mi.resource_name = '快递公司管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (203, '快递公司编辑', '/expressCompany/edit.do', (select ri.id from resource_info ri where ri.res_url='/expressCompany/list.do'),(select mi.id from menu_info mi where mi.resource_name = '快递公司管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (204, '快递公司保存', '/expressCompany/save.do', (select ri.id from resource_info ri where ri.res_url='/expressCompany/list.do'),(select mi.id from menu_info mi where mi.resource_name = '快递公司管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (205, '快递公司删除', '/expressCompany/del.do', (select ri.id from resource_info ri where ri.res_url='/expressCompany/list.do'),(select mi.id from menu_info mi where mi.resource_name = '快递公司管理'),NULL);

/** 4.客户管理 */
/** 客户列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (301, '客户列表', '/customer/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '客户管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (302, '客户详情', '/customer/detail.do', (select ri.id from resource_info ri where ri.res_url='/customer/list.do'),(select mi.id from menu_info mi where mi.resource_name = '客户管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (303, '客户编辑', '/customer/edit.do', (select ri.id from resource_info ri where ri.res_url='/customer/list.do'),(select mi.id from menu_info mi where mi.resource_name = '客户管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (304, '客户保存', '/customer/save.do', (select ri.id from resource_info ri where ri.res_url='/customer/list.do'),(select mi.id from menu_info mi where mi.resource_name = '客户管理'),NULL);
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (305, '客户删除', '/customer/del.do', (select ri.id from resource_info ri where ri.res_url='/customer/list.do'),(select mi.id from menu_info mi where mi.resource_name = '客户管理'),NULL);


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

/** 9.系统管理 */
/** 日志列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (500, '日志列表', '/expressLog/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '日志列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (501, '日志详情', '/expressLog/detail.do', (select ri.id from resource_info ri where ri.res_url='/expressLog/list.do'),(select mi.id from menu_info mi where mi.resource_name = '日志列表'),NULL);

/** 意见列表 */
insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (520, '意见列表', '/suggestion/list.do', 0,(select mi.id from menu_info mi where mi.resource_name = '意见列表'),NULL);

insert into resource_info (id, res_name, res_url, parent_res_id, menu_id, res_remark) values (521, '意见详情', '/suggestion/detail.do', (select ri.id from resource_info ri where ri.res_url='/suggestion/list.do'),(select mi.id from menu_info mi where mi.resource_name = '意见列表'),NULL);
