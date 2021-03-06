	/** 1.快递管理 */
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (1, 0, NULL,'快递管理', 1, 1, 'caseMa-icon yg-icon');
	
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (2, (select mi.id from menu_info mi where mi.resource_name = '快递管理'), NULL,'收件列表', 1, 2, NULL);
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (3, (select mi.id from menu_info mi where mi.resource_name = '快递管理'), NULL,'寄件列表', 2, 2, NULL);
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (4, (select mi.id from menu_info mi where mi.resource_name = '快递管理'), NULL,'柜子取件列表', 3, 2, NULL);
	
	/** 2.订单管理 */
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (10, 0, NULL,'订单管理', 2, 1, 'review-icon yg-icon');
	
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (11, (select mi.id from menu_info mi where mi.resource_name = '订单管理'), NULL,'支付订单列表', 1, 2, NULL);
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (12, (select mi.id from menu_info mi where mi.resource_name = '订单管理'), NULL,'退款订单列表', 2, 2, NULL);
	
	/** 3.快递公司管理 */
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (20, 0, NULL,'快递公司管理', 3, 1, 'book-icon yg-icon');
	
	/** 4.用户管理 */
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (30, 0, NULL,'客户管理', 4, 1, 'user-icon yg-icon');
	
	
	/** 5.权限管理 */
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (70, 0, NULL,'权限管理', 8, 1, 'juri-icon yg-icon');
	
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (71, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'用户列表', 1, 2, NULL);
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (72, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'角色列表', 2, 2, NULL);
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (73, (select mi.id from menu_info mi where mi.resource_name = '权限管理'), NULL,'权限列表', 3, 2, NULL);
	
	/** 6.系统管理 */
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (100, 0, NULL,'系统管理', 9, 1, 'review-icon yg-icon');
	
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (101, (select mi.id from menu_info mi where mi.resource_name = '系统管理'), NULL,'日志列表', 1, 2, NULL);
	insert into menu_info (id, pid, resource_url, resource_name, mindex, level, remark) values (102, (select mi.id from menu_info mi where mi.resource_name = '系统管理'), NULL,'意见列表', 2, 2, NULL);
