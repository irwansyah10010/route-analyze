TRUNCATE TABLE DATABASECHANGELOG;

create table menu (
   id varchar(36) not null,
   menu_name varchar(50) not null,
   module varchar(75) not null,
   parent_id varchar(32),
   menu_url varchar(50),
   created_by varchar(100),
   created_at bigint,
   updated_by varchar(100),
   updated_at bigint,
   is_active bit not null,
   versions integer not null,
   constraint menu_pk primary key (id)
 ) engine=InnoDB;

 create table role (
   id varchar(36) not null,
   role_code varchar(3) not null,
   role_name varchar(30) not null,
   created_by varchar(100),
   created_at bigint,
   updated_by varchar(100),
   updated_at bigint,
   is_active bit not null,
   versions integer not null, 
   constraint role_pk primary key (id)
 ) engine=InnoDB;

create table role_menu (
  id varchar(36) not null,
  menu_id varchar(36) not null,
  role_id varchar(36) not null,
  created_by varchar(100),
  created_at bigint,
  updated_by varchar(100),
  updated_at bigint,
  versions integer not null 
) engine=InnoDB;

alter table role_menu add constraint role_menu_role_fk foreign key (role_id) references role (id);
alter table role_menu add constraint role_menu_menu_fk foreign key (menu_id) references menu (id);

create table user (
   id varchar(36) not null,
   full_name varchar(50) not null,
   email varchar(30) not null,
   place_of_birth varchar(30) not null,
   date_of_birth date not null,
   pass varchar(32) not null,
   address varchar(100),
   role_id varchar(36),
   created_by varchar(100),
   created_at bigint,
   updated_by varchar(100),
   updated_at bigint,
   is_active bit not null,
   versions integer not null, 
   constraint user_pk primary key (id)
 ) engine=InnoDB;

alter table user add constraint user_role_fk foreign key (role_id) references role (id);

CREATE TABLE kurir (
    id varchar(36) NOT NULL PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    nomor_telepon VARCHAR(20),
    created_by varchar(100),
    created_at bigint,
    updated_by varchar(100),
    updated_at bigint,
    is_active bit not null,
    versions integer not null
) engine=InnoDB;

CREATE TABLE laporan (
    id varchar(36) NOT NULL PRIMARY KEY,
    kurir_id varchar(36),
    tanggal_laporan DATE,
    created_by varchar(100),
    created_at bigint,
    updated_by varchar(100),
    updated_at bigint,
    versions integer not null,
    FOREIGN KEY (kurir_id) REFERENCES kurir(id)
) engine=InnoDB;

CREATE TABLE pemasok (
    id varchar(36) NOT NULL PRIMARY KEY,
    nama_pemasok VARCHAR(100) NOT NULL,
    alamat TEXT NOT NULL,
    tandai bit DEFAULT 0,
    latitude DOUBLE NOT NULL, 
    longitude DOUBLE NOT NULL, 
    created_by varchar(100),
    created_at bigint,
    updated_by varchar(100),
    updated_at bigint,
    is_active bit not null,
    versions integer not null
) engine=InnoDB;

CREATE TABLE pengambilan_barang (
    id varchar(36) NOT NULL PRIMARY KEY,
    laporan_id varchar(36),
    pemasok_id varchar(36),
    nama_barang VARCHAR(100) NOT NULL,
    jumlah INT,
    jarak DOUBLE,
    created_by varchar(100),
    created_at bigint,
    updated_by varchar(100),
    updated_at bigint,
    versions integer not null,
    FOREIGN KEY (laporan_id) REFERENCES laporan(id),
    FOREIGN KEY (pemasok_id) REFERENCES pemasok(id)
) engine=InnoDB;

CREATE TABLE pengaturan_bco (
  id varchar(36) NOT NULL PRIMARY KEY,
  max_iterations INT, 
  population_size INT, 
  employed_bees INT, 
  onlooker_bees INT, 
  scout_bees INT, 
  objective TEXT, 
  laporan_id varchar(36) NOT NULL,
  created_by varchar(100),
  created_at bigint,
  updated_by varchar(100),
  updated_at bigint,
  is_active bit not null,
  versions integer not null,
  FOREIGN KEY (laporan_id) REFERENCES laporan(id)
) engine=InnoDB;

CREATE TABLE pengaturan (
  id varchar(36) NOT NULL PRIMARY KEY,
  label varchar(50),
  tipe varchar(20),
  value varchar(25),
  lov varchar(100),
  format varchar(20),
  deskripsi TEXT,
  created_by varchar(100),
  created_at bigint,
  updated_by varchar(100),
  updated_at bigint,
  is_active bit not null,
  versions integer not null
) engine=InnoDB;


CREATE TABLE inisial_solusion (
    id varchar(36) NOT NULL PRIMARY KEY,
    laporan_id varchar(36),
    pengambilan_barang_id varchar(36),
    fitness DOUBLE NOT NULL,
    solusi DOUBLE,
    created_by varchar(100),
    created_at bigint,
    updated_by varchar(100),
    updated_at bigint,
    versions integer not null,
    FOREIGN KEY (laporan_id) REFERENCES laporan(id),
    FOREIGN KEY (pengambilan_barang_id) REFERENCES pengambilan_barang(id)
) engine=InnoDB;