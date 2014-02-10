 /*Insert into Tables*/
INSERT INTO EJAVA.PKID_GEN_TABLE  VALUES ('bubbleId',7);
INSERT INTO EJAVA.PKID_GEN_TABLE  VALUES ('discussionId',5);
INSERT INTO EJAVA.PKID_GEN_TABLE  VALUES ('bubbleUserId',7);

 /*Insert into BUBBLE_BOOK_USER*/
insert into ejava.BUBBLE_BOOK_USER 
values(1,'sathish','pass','captain.png','abt me',CURRENT_TIMESTAMP,'JR@NUS.EDU.SG');

insert into ejava.BUBBLE_BOOK_USER 
values(2,'vincent','pass','hulk.png','abt me',CURRENT_TIMESTAMP,'vinc@NUS.EDU.SG');

insert into ejava.BUBBLE_BOOK_USER 
values(3,'nags','pass','loki.png','abt me',CURRENT_TIMESTAMP,'nags@NUS.EDU.SG');

insert into ejava.BUBBLE_BOOK_USER 
values(4,'aish','pass','widow.png','abt me',CURRENT_TIMESTAMP,'aish@NUS.EDU.SG');

insert into ejava.BUBBLE_BOOK_USER 
values(5,'nitin','pass','ironman.png','abt me',CURRENT_TIMESTAMP,'npk@NUS.EDU.SG');

INSERT INTO EJAVA.BUBBLE_BOOK_USER (USER_ID, NAME, PASSWORD, IMAGE_URL, ABT_ME, LAST_LOGON_TIME, EMAIL) 
        VALUES (6, 'barney','barney','barney.png','abt me',CURRENT_TIMESTAMP,'barney@gmail.com');


/*Insert into BUBBLE_BOOK_USER_FRIENDS*/
insert into ejava.BUBBLE_BOOK_USER_FRIENDS values (1,3);
insert into ejava.BUBBLE_BOOK_USER_FRIENDS values (1,4);


/*Insert into DISCUSSION*/
insert into ejava.DISCUSSION 
values (1,'ejava discussion 1','discussion description 1',3,'2013-12-02 12:12:00.660');

insert into ejava.DISCUSSION 
values (2,'ejava discussion 2','discussion description 2',2,'2013-12-03 18:52:00.660');

insert into ejava.DISCUSSION 
values (3,'ejava discussion 3','discussion description 3',2,'2013-12-04 21:25:00.660');

insert into ejava.DISCUSSION 
values (4,'ejava discussion 4','discussion description 4',2,'2013-12-07 16:12:00.660');


/*Insert into DISCUSSION_PARTICIPANTS*/
insert into ejava.DISCUSSION_PARTICIPANTS (DISCUSSION_ID, USER_ID) values (1,2), (2,3), (2,4);

/*Insert into BUBBLE*/
insert into ejava.BUBBLE
values (2,3,'2013-12-02 12:12:00.660',1,'TEXT','lets start a discussion');

insert into ejava.BUBBLE 
values (4,3,'2013-12-04 21:25:00.660',1,'TEXT','hi guys watsup');

insert into ejava.BUBBLE
values (5,3,'2013-12-06 21:25:00.660',1,'TEXT','hello how r u?');

insert into ejava.BUBBLE
values (6,3,'2013-12-08 21:25:00.660',1,'TEXT','i m fine !!! ');

insert into ejava.BUBBLE 
values (1,3,'2013-12-02 12:12:00.660',2,'MAP','lets meet here');
insert into ejava.BUBBLE_MAP values (1,12.73,45.34,'ISS');

insert into ejava.BUBBLE
values (3,3,'2013-12-04 21:25:00.660',2,'MAP','venue changes');
insert into ejava.BUBBLE_MAP values (3,12.73,45.34,'NUS');


/*Insert into BUBBLE_HIERARCHY*/
insert into ejava.BUBBLE_HIERARCHY(bubble_id,parent_bubble_id)
values (2,null);

insert into ejava.BUBBLE_HIERARCHY(bubble_id,parent_bubble_id)
values (4,2);

insert into ejava.BUBBLE_HIERARCHY(bubble_id,parent_bubble_id)
values (5,null);

insert into ejava.BUBBLE_HIERARCHY(bubble_id,parent_bubble_id)
values (6,2);

insert into ejava.BUBBLE_HIERARCHY(bubble_id,parent_bubble_id)
values (1,null);

insert into ejava.BUBBLE_HIERARCHY(bubble_id,parent_bubble_id)
values (3,1);
