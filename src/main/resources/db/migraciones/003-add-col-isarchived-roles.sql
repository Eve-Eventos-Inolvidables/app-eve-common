--liquibase formatted sql
--changeset mrfabian:003-add-col-isarchived-roles

ALTER TABLE roles
ADD is_archived bool;