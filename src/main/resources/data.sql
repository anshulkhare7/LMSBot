/*
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `courses` mediumtext,
  `chat_id` int(11) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `chat_id_UNIQUE` (`chat_id`),
  UNIQUE KEY `mobile_UNIQUE` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `details` varchar(256) NOT NULL,
  `code` varchar(45) NOT NULL,
  `videos` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO user(id, name, mobile, role) values('100001', 'Anshul Khare', '919980066866', 'ADMIN');
INSERT INTO user(id, name, mobile, role) values('100002', 'Ashish Shukla', '919651160056', 'ADMIN');

INSERT INTO course(id, code, name, details, status, videos) values('100001', 'SLC-01', 'Zenyoga Basic Course', 'Best Course on Spirituality', 'VALID', 'https://youtu.be/w_OLzeoEims##https://youtu.be/Cs9g7M2sOGs##https://youtu.be/jYJHHeMyZLU##https://youtu.be/TBcSNUeEOYw##https://youtu.be/-smIS9ucYIA##https://youtu.be/T2vduf6L2xE##https://youtu.be/9et-6Ianbyo##https://youtu.be/deeJuB30OsI##https://youtu.be/mdtOD3Z7oJ4##https://youtu.be/D-X9TloL-WQ##https://youtu.be/ejvYfqQjGcA##https://youtu.be/EDAPk4QGxyc##https://youtu.be/PrNI39qqjks##https://youtu.be/10ec0usDraM##https://youtu.be/upOogs9p6_Y##https://youtu.be/y2XGVynUTE8##https://youtu.be/X5m49sBSKGI##https://youtu.be/tzSyrDsfHX8##https://youtu.be/CHL3u8DHhEY##https://youtu.be/HQtOGi6t710##https://youtu.be/Fx91-h2B_jg##https://youtu.be/lO_Y7wKXMFs##https://youtu.be/lobWPHvRc-I##https://youtu.be/M2g-eaBmpeI##https://youtu.be/AawjMfPqEvg##https://youtu.be/pyLXk7y0olA##https://youtu.be/8r_yZSaFUL0##https://youtu.be/C1Yn0RWMh2w##https://youtu.be/BADprJFCCIQ##https://youtu.be/ctDYCSr4-2Q##https://youtu.be/ejZjHJ07Ys4##https://youtu.be/wrH6Qt3450A##https://youtu.be/At_j4ZBVF0M##https://youtu.be/_x5Wx9TRsDY##https://youtu.be/HASD85nxqCU##https://youtu.be/WFN2f003-_U##https://youtu.be/tzxxquFzWS4##https://youtu.be/3EAbz0F7us4##https://youtu.be/jNufYj_bwcI##https://youtu.be/s01gu0KrIkk##https://youtu.be/fe8mubp3JUo##https://youtu.be/PWQfd0bdacg##https://youtu.be/5FQ9mrpwr6Q##https://youtu.be/-zyzUT6OKnU##https://youtu.be/FrUXSNdLglI##https://youtu.be/9ObKYi3LpFU##https://youtu.be/ANNYR3naVL0##https://youtu.be/GvLYGirDr24##https://youtu.be/8V-0lrVaINs##https://youtu.be/db8b4APR_oM##https://youtu.be/xxw2P2mDpGs##https://youtu.be/CyUlF144Bms##https://youtu.be/K08CEj2ZeNY##https://youtu.be/kTPIdRkLUq4##https://youtu.be/LvwED916ovQ##https://youtu.be/tvxfzSFROvM##https://youtu.be/5N5TTQ23lBE##https://youtu.be/ywFM-seCIxM##https://youtu.be/eUQ230_yWUU##https://youtu.be/LpXx1xmIpH0##https://youtu.be/e3yLTBQ0xKM##https://youtu.be/bUkJge7b8zU##https://youtu.be/rl32-2fmxlQ##https://youtu.be/5X7VxjlDZ-w##https://youtu.be/n1GXnE_VkQ4##https://youtu.be/uy1GWvITihQ##https://youtu.be/YvCd764G-F0##https://youtu.be/nPJBNcR7UoM##https://youtu.be/gmDQ4cQPHPs##https://youtu.be/08fQUui-fKo##https://youtu.be/hoZI8ORdnfg##https://youtu.be/AGwV7XjnT6Y##https://youtu.be/U1KasXqedFM##https://youtu.be/2A9FSpzA_94##https://youtu.be/VvwSSg-4Tp8##https://youtu.be/0V68ViNcylg##https://youtu.be/uD9UG1VI6Sc##https://youtu.be/U2yOZCvqoeo##https://youtu.be/keY4cGyd1zI##https://youtu.be/jllKkf-LkSQ##https://youtu.be/FgEHSdHdQ9c##https://youtu.be/2-BArQqGSgo##https://youtu.be/ZhwkhqdIGow##https://youtu.be/oq0Wr2FImEM##https://youtu.be/osT4IfW-wRk##https://youtu.be/1G5SG70daII##https://youtu.be/Ajytd7m4JX4##https://youtu.be/hbNDZ-U4CfA##https://youtu.be/SOfKQo9OwAA##https://youtu.be/Z2vOKaTil3Q##https://youtu.be/yO9JRs9oZqof##https://youtu.be/Ey4t-3_SaTI##https://youtu.be/lnGCjc06FwA##https://youtu.be/lysVU2L69Jc##https://youtu.be/QdigeqNJmXE##https://youtu.be/doXzlWq0OSU##https://youtu.be/RkS5dDyDSgk##https://youtu.be/XFZhK-569PU##https://youtu.be/HOtzx2zFGcE##https://youtu.be/OfsFYGdHkzw##https://youtu.be/2EQRfhEorNs##https://youtu.be/-Hu5cpTCKqQ##https://youtu.be/lRHu3R3MIRc##https://youtu.be/9G-A5S-owRg##https://youtu.be/GOTGhcrTHJQ##https://youtu.be/VgiZGsmIo6k##https://youtu.be/DES7OGRr-lQ##https://youtu.be/sgYqFgSiJUM##https://youtu.be/d08gHNRapTo##https://youtu.be/4F50Z3viiP8##https://youtu.be/fNbBfP6qeNY##https://youtu.be/lfzaPLeRvvs##https://youtu.be/sG4TH_8blW0##https://youtu.be/wCwvACUEpao##https://youtu.be/BymrQowzftA##https://youtu.be/B8Ua_CR7N3g##https://youtu.be/S-A4NjWQgYg##https://youtu.be/9PVQ5THTUqM');
INSERT INTO course(id, code, name, details, status, videos) values('100002', 'SLC-02', 'World Class Business Execution', 'Most Important Course for scaling your business by 10x', 'VALID', 'https://youtu.be/WJp-veuXPPU##https://youtu.be/WLRAXIfad0U##https://youtu.be/lnFW7yLYj-U##https://youtu.be/lOVzqnZ2z_M##https://youtu.be/Uq2FbvBrAQk##https://youtu.be/1H3jQfLnGnc##https://youtu.be/5DcVEykl0p4##https://youtu.be/YJvOA1BXJ7s##https://youtu.be/6YIj_YHWt6U##https://youtu.be/OQOmPOywdm8##https://youtu.be/Ikrqe7e-9dM##https://youtu.be/6o86HfBace4##https://youtu.be/8GrU7kVsPMU##https://youtu.be/B_HvxY94OPc##https://youtu.be/5bIMfGUcDm0##https://youtu.be/oXrdzdalCNM##https://youtu.be/jTiS2wmaNwA##https://youtu.be/UIwYiqxkE9o##https://youtu.be/uNUYstP-uh4##https://youtu.be/Fce5ETkerM8##https://youtu.be/m7BRraIbY7A##https://www.youtube.com/watch?v=XGKzW9IxRbo##https://www.youtube.com/watch?v=qsbT2QRWxKM##https://www.youtube.com/watch?v=rTA7RMEGsP4##https://www.youtube.com/watch?v=sLur-A8HpIw##https://www.youtube.com/watch?v=bhhQWMSZUjY##https://www.youtube.com/watch?v=FvZgYkeJAA4&t=7s##https://www.youtube.com/watch?v=wzT83_UvwDo##https://www.youtube.com/watch?v=XImPDXao5hk##https://www.youtube.com/watch?v=4b10nqI7g0I##https://www.youtube.com/watch?v=alp_vGvppIE##https://www.youtube.com/watch?v=Nv8bz7IP6Z4##https://www.youtube.com/watch?v=tcgLVFI6qAY##https://www.youtube.com/watch?v=byApEU4ekiU##https://www.youtube.com/watch?v=SJeOr4_Jw7A##https://www.youtube.com/watch?v=cOQMSJyBAnw##https://www.youtube.com/watch?v=obf-3ojfQGI##https://www.youtube.com/watch?v=7_pSDwL7dNk##https://www.youtube.com/watch?v=Pxhh3HB_gLk##https://www.youtube.com/watch?v=L2qa0Zb2J-k##https://www.youtube.com/watch?v=OKT-ZnZGO1w##https://www.youtube.com/watch?v=F-CKFzEgic0##https://www.youtube.com/watch?v=nSDvfCw6Z30##https://www.youtube.com/watch?v=0k7JDD8GcpA##https://www.youtube.com/watch?v=YuS0Y0TawSk##https://www.youtube.com/watch?v=ILhJTzNXzrY##https://www.youtube.com/watch?v=ku2u4YYfbmQ##https://www.youtube.com/watch?v=A4mYjIHCFeA##https://www.youtube.com/watch?v=UXkSQVO9umo##https://www.youtube.com/watch?v=pFzYNZbhVEE##https://www.youtube.com/watch?v=pIMKq8s0khI##https://www.youtube.com/watch?v=oU93LvBC_mk##https://youtu.be/sv71d9KzXP8##https://www.be/5eNMEqrCCSM##https://www.be/bT-EVDEvpME##https://www.be/Oti7Yp6s_-U##https://www.be/Gx23mOhpuuQ##https://www.be/_cZSeLp6avk##https://www.youtube##https://www.youtube##https://www.be/hvIAvKXV4l8##https://www.be/jF4IAqFwuYk##https://www.be/bnr6OAe5TSc##https://www.be/qnyoV507HKo##https://www.be/UjcJZkQ41t8##https://www.be/TWFZVJ3Ulnk##https://www.be/_bs5OsB8ofk##https://www.be/cNOiN4LEBC4##https://www.be/trI8VzrOZ04##https://www.be/ejdXmbMlKNE##https://www.be/c9JnqykYDxg##https://www.be/VvXonIWiwuQ##https://www.be/De-BO4TTGHU##https://www.be/EwIz8sG9ay4##https://www.be/TOEAL3hP5o8##https://www.be/ea63AtrGU4E##https://www.be/Q0_TmNNRKBM##https://www.be/csbUwGZdMhI##https://www.be/ZaGP8Mf153A##https://www.be/XQKHiTX1fkc##https://www.be/yKCTZMc47ww##https://www.be/1wBaoC9SmBU##https://www.be/ZSuRdIgt-2E##https://www.be/6PePP_ngebc##https://youtu.be/J6fQnd0e_0s##https://youtu.be/Go_4xDPTUaM##https://youtu.be/1wqzTSsFJME##https://youtu.be/N8a_kI4HwPQ##https://youtu.be/dYTJMBVBtIk##https://youtu.be/s5PwyWfKcOo##https://www.be/OAOZg7RWllY##https://www.be/c31JveXsRIw##https://youtu.be/5bNCjE34svE##https://youtu.be/jtIFMlHFong##https://youtu.be/8a9eO7wFkNg##https://youtu.be/hJ4DL07xu9g');
*/