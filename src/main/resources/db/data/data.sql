-- Role
insert into role(id,role_code, role_name, created_at, created_by, is_active, versions)
values('initiated-ga','GA', 'Grand Admin', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated System', true,1);

-- User
insert into user(id,full_name, place_of_birth, date_of_birth, address, email, pass, role_id, created_at, created_by, is_active, versions)
values('initiated-g','Grand', 'system', '2024-08-01', 'system', 'sys.admin@admin.com','12345', 'initiated-ga', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated System', true,1 );

-- Menu
insert into menu(id, menu_name, module, parent_id,created_at, created_by, is_active, versions)
values('initiated-control','Control', 'control',null, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', true,1);

insert into menu(id, menu_name, module, parent_id,menu_url,created_at, created_by, is_active, versions)
values('initiated-menu','Menu', 'control/menu','initiated-control','/menu/', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', true,1);

insert into menu(id, menu_name, module, parent_id,menu_url,created_at, created_by, is_active, versions)
values('initiated-role','Role', 'control/role','initiated-control','/role/', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', true,1);

insert into menu(id, menu_name, module, parent_id,menu_url,created_at, created_by, is_active, versions)
values('initiated-user','User', 'control/user','initiated-control','/user/', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', true,1);


-- role menu
insert into role_menu(id ,menu_id, role_id, created_at, created_by, versions)
values('initiated-ga-ctl','initiated-control','initiated-ga', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', 1);

insert into role_menu(id ,menu_id, role_id, created_at, created_by, versions)
values('initiated-ga-mn','initiated-menu','initiated-ga', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', 1);

insert into role_menu(id ,menu_id, role_id, created_at, created_by, versions)
values('initiated-ga-rl','initiated-role','initiated-ga', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', 1);

insert into role_menu(id ,menu_id, role_id, created_at, created_by, versions)
values('initiated-ga-us','initiated-user','initiated-ga', ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),'initiated-g', 1);

-- pemasok
INSERT INTO beecolonyoptimation.pemasok
(id, nama_pemasok, tandai, alamat, latitude, longitude, created_by, created_at, updated_by, updated_at, is_active, versions)
VALUES('my-location', 'Ter', 1, 'Jalan Piol; Kelapa Gading; Daerah Khusus Ibukota Jakarta; ', -6.172572721799255, 106.90965951110712, 'System', 1728737955140, NULL, NULL, 1, 0);

-- pengaturan 
INSERT INTO pengaturan (id,label,tipe,value,format,deskripsi,created_by,created_at,updated_by,updated_at,is_active,versions) VALUES
('max_iterations','max_iterations','BCO','100','NUMBER','Max Iterations','initiated-system',1728737955140,NULL,NULL,1,1),
('population_size','population_size','BCO','50','NUMBER','Population Size','initiated-system',1728737955140,NULL,NULL,1,1),
('employed_bees','employed_bees','BCO','25','NUMBER','Employee Bees','initiated-system',1728737955140,NULL,NULL,1,1),
('onlooker_bees','onlooker_bees','BCO','25','NUMBER','Onlooker Bees','initiated-system',1728737955140,NULL,NULL,1,1),
('scout_bees','scout_bees','BCO','5','NUMBER','Scout Bees','initiated-system',1728737955140,NULL,NULL,1,1),
('objective','objective','BCO','RUTE OPTIMATION','TEXT','objective','initiated-system',1728737955140,NULL,NULL,1,1);


INSERT INTO pengaturan (id,label,tipe,value,lov,format,deskripsi,created_by,created_at,updated_by,updated_at,is_active,versions) VALUES
('traffic_conditions','Kondisi Lalu Lintas','PARAM', '1.0','{"0.6":"padat","0.4":"macet","1.0":"lancar"}','JSON','Trafic Condition','initiated-system',1728737955140,NULL,NULL,1,1),
('weather_conditions','Kondisi Cuaca','PARAM','1.0','{"0.75":"hujan","1.0":"cerah"}','JSON','Weather Condition','initiated-system',1728737955140,NULL,NULL,1,1),
('vehicle_type','Jenis Kendaraan','PARAM','1.0','{"0.75":"mobil","1.0":"motor"}','JSON','Weather Condition','initiated-system',1728737955140,NULL,NULL,1,1);


