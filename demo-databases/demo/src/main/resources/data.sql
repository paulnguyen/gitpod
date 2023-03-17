-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile



/* Yukihiro Matz Matsumoto */

insert into person ( id, first_name, last_name, birth_date )
values  (8, 'Yukihiro', 'Matsumoto (aka Matz)', '1965-04-14' ) ;


/* James Gosling */

insert into person ( id, first_name, last_name, birth_date )
values  (9, 'James', 'Gosling', '1955-05-19' ) ;


/* Martin Odersky */

insert into person ( id, first_name, last_name, birth_date )
values  (10, 'Martin', 'Odersky', '1958-09-05' ) ;




/* Yukihiro Matz Matsumoto */

insert into contribs ( person_id, id, contribution ) values ( 8, 19, 'Ruby' ) ;


/* James Gosling */

insert into contribs ( person_id, id, contribution ) values ( 9, 20, 'Java' ) ;



/* Martin Odersky */

insert into contribs ( person_id, id, contribution ) values ( 10, 21, 'Scala' ) ;
