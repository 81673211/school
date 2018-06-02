/** 商品管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (1, 0, NULL,'商品管理', 1, 1, 'caseMa-icon yg-icon');

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (2, (select mi.id from menu_info mi where mi.resource_name = '商品管理'), NULL,'分类列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (3, (select mi.id from menu_info mi where mi.resource_name = '商品管理'), NULL,'品牌列表', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (4, (select mi.id from menu_info mi where mi.resource_name = '商品管理'), NULL,'商品列表', 3, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (5, (select mi.id from menu_info mi where mi.resource_name = '商品管理'), NULL,'商品审核列表', 4, 2, NULL);

/** 订单管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (10, 0, NULL,'订单管理', 2, 1, 'review-icon yg-icon');

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (11, (select mi.id from menu_info mi where mi.resource_name = '订单管理'), NULL,'订单列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (12, (select mi.id from menu_info mi where mi.resource_name = '订单管理'), NULL,'退货单列表', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (13, (select mi.id from menu_info mi where mi.resource_name = '订单管理'), NULL,'换货单列表', 3, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (14, (select mi.id from menu_info mi where mi.resource_name = '订单管理'), NULL,'物流列表', 4, 2, NULL);

/** 会员管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (20, 0, NULL,'会员管理', 3, 1, 'stat-icon yg-icon');

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (21, (select mi.id from menu_info mi where mi.resource_name = '会员管理'), NULL,'会员列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (22, (select mi.id from menu_info mi where mi.resource_name = '会员管理'), NULL,'黑名单列表', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (23, (select mi.id from menu_info mi where mi.resource_name = '会员管理'), NULL,'积分类型列表', 3, 2, NULL);

/** 分析管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (30, 0, NULL,'分析管理', 4, 1, 'book-icon yg-icon');

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (31, (select mi.id from menu_info mi where mi.resource_name = '分析管理'), NULL,'用户统计', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (32, (select mi.id from menu_info mi where mi.resource_name = '分析管理'), NULL,'商品销售统计', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (33, (select mi.id from menu_info mi where mi.resource_name = '分析管理'), NULL,'充值统计', 3, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (34, (select mi.id from menu_info mi where mi.resource_name = '分析管理'), NULL,'订单统计', 4, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (35, (select mi.id from menu_info mi where mi.resource_name = '分析管理'), NULL,'积分统计', 5, 2, NULL);

/** 充值管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (40, 0, NULL,'充值管理', 5, 1, 'user-icon yg-icon');

/** 内容管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (50, 0, NULL,'内容管理', 6, 1, 'news-icon yg-icon');

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (51, (select mi.id from menu_info mi where mi.resource_name = '内容管理'), NULL,'banner', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (52, (select mi.id from menu_info mi where mi.resource_name = '内容管理'), NULL,'常见问题', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (53, (select mi.id from menu_info mi where mi.resource_name = '内容管理'), NULL,'公告报道', 3, 2, NULL);

/** app管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (60, 0, NULL,'app管理', 7, 1, 'juri-icon yg-icon');

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (61, (select mi.id from menu_info mi where mi.resource_name = 'app管理'), NULL,'栏目列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (62, (select mi.id from menu_info mi where mi.resource_name = 'app管理'), NULL,'版本列表', 2, 2, NULL);

/** 权限管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (70, 0, NULL,'权限管理', 8, 1, NULL);

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (71, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'用户列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (72, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'角色列表', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (73, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'权限列表', 3, 2, NULL);

/** 代理商管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (80, 0, NULL,'代理商管理', 9, 1, NULL);

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (81, (select mi.id from menu_info mi where mi.resource_name = '代理商管理'), NULL,'代理商列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (82, (select mi.id from menu_info mi where mi.resource_name = '代理商管理'), NULL,'下属经销商列表', 2, 2, NULL);
/**insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (83, (select mi.id from menu_info mi where mi.resource_name = '代理商管理'), NULL,'代理商个人列表', 3, 2, NULL);*/

/** 经销商管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (90, 0, NULL,'经销商管理', 10, 1, NULL);

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (91, (select mi.id from menu_info mi where mi.resource_name = '经销商管理'), NULL,'经销商列表', 1, 2, NULL);
/**insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (92, (select mi.id from menu_info mi where mi.resource_name = '经销商管理'), NULL,'经销商个人列表', 2, 2, NULL);*/

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (100, 0, NULL,'短信管理', 11, 1, NULL);



/**insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (110, 0, NULL,'老中医问诊管理', 11, 2, NULL);*/
/**insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (120, 0, NULL,'金融贷款管理', 12, 1, NULL);*/