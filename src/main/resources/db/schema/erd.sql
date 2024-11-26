TABLE kurir {
    id VARCHAR [PRIMARY KEY]
    nama VARCHAR(100) 
    nomor_telepon VARCHAR(20)
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    is_active bit 
    versions integer 
}

TABLE laporan {
    id VARCHAR [PRIMARY KEY]
    kurir_id VARCHAR
    tanggal_laporan DATE
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    versions integer 
}

TABLE pemasok {
    id VARCHAR [PRIMARY KEY]
    nama_pemasok VARCHAR(100) 
    alamat VARCHAR(255) 
    latitude DOUBLE 
    longitude DOUBLE 
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    is_active bit 
    versions integer 
}

TABLE pengambilan_barang {
    id VARCHAR [PRIMARY KEY]
    laporan_id VARCHAR
    pemasok_id VARCHAR
    nama_barang VARCHAR(100) 
    jumlah INT
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    versions integer 
}

TABLE hasil {
    id VARCHAR [PRIMARY KEY]
    laporan_id VARCHAR
    pengambilan_barang_id_id VARCHAR
    fitness DOUBLE
    solusi DOUBLE
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    versions integer 
}

TABLE pengaturan {
    id VARCHAR [PRIMARY KEY]
    label VARCHAR
    tipe VARCHAR
    value VARCHAR(100) 
    lov VARCHAR
    format VARCHAR
    deskripsi VARCHAR
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    versions integer 
}

TABLE role {
    id VARCHAR [PRIMARY KEY]
    role_name VARCHAR
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    versions integer 
}

TABLE user {
    id VARCHAR [PRIMARY KEY]
    full_name VARCHAR
    email VARCHAR
    place_of_birth VARCHAR(100) 
    date_of_birth date
    pass VARCHAR(32)
    role_id VARCHAR
    created_by varchar(100)
    created_at bigint
    updated_by varchar(100)
    updated_at bigint
    versions integer 
}

Ref: laporan.kurir_id < kurir.id
Ref: pengambilan_barang.laporan_id < laporan.id
Ref: pengambilan_barang.pemasok_id < pemasok.id

Ref: user.role_id < role.id
Ref: hasil.laporan_id < laporan.id
Ref: hasil.pengambilan_barang_id_id < pengambilan_barang.id

