--
-- PostgreSQL database dump
--

DROP TABLE IF EXISTS public.computer, public.report, public.logs CASCADE;
DROP SEQUENCE IF EXISTS public.sequencer_computer, public.sequencer_report, public.sequencer_logs;

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;


--
-- TOC entry 174 (class 3079 OID 11769)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

-- CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

-- COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


-- SET search_path = public, pg_catalog;

SET default_tablespace = '';
SET default_with_oids = false;


CREATE SEQUENCE public.sequencer_computer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
CREATE SEQUENCE public.sequencer_report
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.sequencer_logs
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
--
-- TABLE: user
--

CREATE TABLE public.computer (
    id bigint NOT NULL,
    name varchar(100) NOT NULL UNIQUE
);

ALTER TABLE public.computer ADD CONSTRAINT pk_computer PRIMARY KEY (id);

ALTER SEQUENCE public.sequencer_computer OWNED BY public.computer.id;
ALTER TABLE ONLY public.computer ALTER COLUMN id SET DEFAULT nextval('sequencer_computer'::regclass);


--
-- TABLE: device
--

CREATE TABLE public.report (
    id bigint NOT NULL,
    computer_id bigint NOT NULL,
    upload_date timestamp
);

ALTER TABLE public.report ADD CONSTRAINT pk_report PRIMARY KEY (id);

ALTER SEQUENCE public.sequencer_report OWNED BY public.report.id;
ALTER TABLE ONLY public.report ALTER COLUMN id SET DEFAULT nextval('sequencer_report'::regclass);
ALTER TABLE public.report ADD CONSTRAINT fk1_report FOREIGN KEY (computer_id) REFERENCES public.computer (id) ON DELETE CASCADE;


--
-- TABLE: logs
--

CREATE TABLE public.logs (
    id bigint NOT NULL,
    report_id bigint NOT NULL,
    client_date timestamp,
    priority varchar(10) NOT NULL,
    thread varchar(40) NOT NULL,
    package varchar(30) NOT NULL,
    file_name varchar(100) NOT NULL,
    file_line integer NOT NULL,
    message varchar(50000) NOT NULL
);

ALTER TABLE public.logs ADD CONSTRAINT pk_logs PRIMARY KEY (id);

ALTER SEQUENCE public.sequencer_logs OWNED BY public.logs.id;
ALTER TABLE ONLY public.logs ALTER COLUMN id SET DEFAULT nextval('sequencer_logs'::regclass);
ALTER TABLE public.logs ADD CONSTRAINT fk1_logs FOREIGN KEY (report_id) REFERENCES public.report (id) ON DELETE CASCADE;
