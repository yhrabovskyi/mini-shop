create table USERS (USER_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT USERS_PK PRIMARY KEY, LOGIN varchar(32) NOT NULL, HASH varchar(64) NOT NULL, U_PRVLGS integer NOT NULL, F_NAME varchar(32) NOT NULL, S_NAME varchar(32) NOT NULL);

create table ITEMS (ITEM_ID integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT ITEMS_PK PRIMARY KEY, NAME varchar(40) NOT NULL, PRICE integer NOT NULL, QUANT integer NOT NULL, DESCR clob NOT NULL);

create table BASKETS (USER_ID integer NOT NULL, ITEMS clob NOT NULL, FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID));

insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 10, 'FName', 'SName');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('se_admin', 'ab95f7882957af4ebbcf8be3ba558d04d6640f62aa814699867769676e6d2704', 10, 'Bohdan', 'Phorte');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('ainy', '8b610d8266dd1c6233c4b951d27cccc717d27f38033df0db554951973bc962af', 40, 'Anny', 'Ailholder');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('th_admin', '1d695a48d74b4544f37e480289f08c724251ca74d61f94802a9b364a8f5f560d', 10, 'Matthew', 'Reginhthor');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('var_20', '7bebba15544865e1ac174dac13cdbb9401f28ff30d9f3b47d5fc02fd55fe425e', 20, 'Christopher', 'Munday');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('moon', '9e78b43ea00edcac8299e0cc8df7f6f913078171335f733a21d5d911b6999132', 40, 'Ian', 'Robertson');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('main_manag', '4c3d50e4621ae89395afb3623761b9fc0be37359fd8ba03fe796057e3d881add', 20, 'Andrew', 'Kolender');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('washin', '793fb43ad0e41ff9d24b9bc71011f5f35ae9b90ce7c33715dc03cea8831b84b8', 40, 'Emma', 'Washington');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('kko_231', 'cfe66186da10cb3617485c7ff4752f93736148db2727fa84c0caecd75ec55e9f', 10, 'Sarah', 'Liberty');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('shamp', '6758cdd9f131bcc51b75f63c567f7049fea4283ef58a11299899dde8e2a7502d', 40, 'Tom', 'Washington');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('liti', '86fd4be33502c72915335c382f52a90e445a048748215b069919a361c0bef214', 40, 'Jeff', 'Aitum');
insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) values('Dan', 'b1259567b8a27cd0ee0ce4c79d0670c75bada9e86dcdeff374ffd922d41cbe7e', 40, 'Dan', 'Sinpurt');

insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Laptop 100Z2', 637, 40, 'Processor M23 2 GHz, RAM 6 GB, HD 1 TB, Wi-Fi 802.11 b/g/n');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Smartphone Q2', 231, 3, 'Processor GMob 1.2 GHz, RAM 1 GB, HD 1 TB, Wi-Fi 802.11 b/g/n, GSM 900/1800, LTE');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Smartphone MR23', 209, 1, 'New phone with ultimate OS, processor 1.3 GHz, RAM 1 GB, HD 1 TB, LTE');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Processor T90', 55, 100, '1.3 GHz, 2 Cores');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Processor T92M', 51, 0, '2.3 GHz, 4 Cores');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Smartphone MR23RevC', 220, 0, 'OS 2.1, processor 1.5 GHz, RAM 2 GB, HD 1 TB, LTE');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Smartphone A7A1', 550, 0, 'OS 2.0, processor 1.4 GHz, RAM 2 GB, HD 64 GB, 3G');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Processor T92M1', 51, 0, '2.3 GHz, 6 Cores');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Smartphone MR23RevD', 220, 10, 'OS 2.1, processor 1.6 GHz, RAM 4 GB, HD 1 TB, LTE');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Smartphone PO2', 209, 7, 'New phone with ultimate OS, processor 1.3 GHz, RAM 1 GB, HD 1 TB, LTE');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Laptop RM32Q', 789, 40, 'Processor K45 2 GHz, RAM 6 GB, HD 1 TB, Wi-Fi 802.11 b/g/n');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Laptop 100Z1', 654, 41, 'Processor M1 2.5 GHz, RAM 6 GB, HD 1 TB, Wi-Fi 802.11 b/g/n');
insert into ITEMS (NAME, PRICE, QUANT, DESCR) values('Laptop Boo', 432, 20, 'Processor M3 2.2 GHz, RAM 6 GB, HD 1 TB, Wi-Fi 802.11 b/g/n');
