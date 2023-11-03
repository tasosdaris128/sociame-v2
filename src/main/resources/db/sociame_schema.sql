--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0 (Debian 16.0-1.pgdg120+1)
-- Dumped by pg_dump version 16.0 (Debian 16.0-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- Name: search_account(text); Type: FUNCTION; Schema: public; Owner: user
--

CREATE FUNCTION public.search_account(term text) RETURNS TABLE(id bigint, user_id bigint, username text, first_name text, last_name text, gender text, plan integer, verified boolean)
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN QUERY 
		SELECT
			a.id::bigint,
			a.user_id::bigint,
			u.username,
			a.first_name,
			a.last_name,
			a.gender,
			a.plan,
			a.verified
		FROM
			users u, account a
		WHERE
			(LOWER(a.first_name) LIKE '%' || LOWER(term) || '%' OR LOWER(a.last_name) LIKE '%' || LOWER(term) || '%' ) AND a.user_id = u.id;
END
$$;


ALTER FUNCTION public.search_account(term text) OWNER TO "user";

--
-- Name: user_account(character varying); Type: FUNCTION; Schema: public; Owner: user
--

CREATE FUNCTION public.user_account(email character varying) RETURNS TABLE(account_id bigint, user_id bigint, username character varying, first_name character varying, last_name character varying, gender character varying, dob timestamp without time zone, plan integer, verified boolean)
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN QUERY 
		SELECT
			a.id::bigint,
			a.user_id::bigint,
			u.username::varchar,
			a.first_name::varchar,
			a.last_name::varchar,
			a.gender::varchar,
			a.dob,
			a.plan,
			a.verified
		FROM
			users u, account a
		WHERE
			u.username = email AND a.user_id = u.id;
END;$$;


ALTER FUNCTION public.user_account(email character varying) OWNER TO "user";

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.account (
    id integer NOT NULL,
    first_name text,
    last_name text,
    gender text,
    dob timestamp without time zone,
    plan integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    user_id bigint,
    verified boolean,
    pin integer,
    verification_token text
);


ALTER TABLE public.account OWNER TO "user";

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.account_id_seq OWNER TO "user";

--
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;


--
-- Name: comment; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.comment (
    id integer NOT NULL,
    body text,
    post_id bigint,
    author_id bigint
);


ALTER TABLE public.comment OWNER TO "user";

--
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comment_id_seq OWNER TO "user";

--
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.comment_id_seq OWNED BY public.comment.id;


--
-- Name: follow; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.follow (
    id integer NOT NULL,
    follower bigint,
    following bigint
);


ALTER TABLE public.follow OWNER TO "user";

--
-- Name: follow_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.follow_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.follow_id_seq OWNER TO "user";

--
-- Name: follow_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.follow_id_seq OWNED BY public.follow.id;


--
-- Name: post; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.post (
    id integer NOT NULL,
    title text,
    body text,
    author_id bigint
);


ALTER TABLE public.post OWNER TO "user";

--
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.post_id_seq OWNER TO "user";

--
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.post_id_seq OWNED BY public.post.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username text,
    password text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    enabled boolean,
    expired boolean,
    locked boolean,
    credentials_expired boolean,
    authorities text[]
);


ALTER TABLE public.users OWNER TO "user";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO "user";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: account id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);


--
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.comment ALTER COLUMN id SET DEFAULT nextval('public.comment_id_seq'::regclass);


--
-- Name: follow id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.follow ALTER COLUMN id SET DEFAULT nextval('public.follow_id_seq'::regclass);


--
-- Name: post id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.post ALTER COLUMN id SET DEFAULT nextval('public.post_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: users constraint_name; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT constraint_name UNIQUE (username);


--
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: post author_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES public.account(id);


--
-- Name: comment author_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES public.account(id);


--
-- Name: comment post_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT post_id_fk FOREIGN KEY (post_id) REFERENCES public.post(id);


--
-- Name: account user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

