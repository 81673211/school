/** 1.快递管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (1, 0, NULL,'快递管理', 1, 1, 'caseMa-icon yg-icon');

/** 2.订单管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (10, 0, NULL,'订单管理', 2, 1, 'review-icon yg-icon');

/** 3.快递公司管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (20, 0, NULL,'快递公司管理', 3, 1, 'book-icon yg-icon');

/** 4.用户管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (30, 0, NULL,'客户管理', 4, 1, 'stat-icon yg-icon');


/** 5.权限管理 */
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (70, 0, NULL,'权限管理', 8, 1, NULL);

insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (71, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'用户列表', 1, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (72, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'角色列表', 2, 2, NULL);
insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (73, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'权限列表', 3, 2, NULL);


