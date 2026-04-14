--
-- PostgreSQL database dump
--

\restrict qWO9AgPn5jWbYj4udq7p6UUcaTOLPj2wJczDkIvVoN4BIwmOXBgwxCClO1sfjZT

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-04-14 16:58:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16466)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id bigint NOT NULL,
    nombre character varying(100) NOT NULL,
    correo character varying(150) NOT NULL,
    telefono character varying(20),
    estado boolean DEFAULT true
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16465)
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.cliente ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 228 (class 1259 OID 16584)
-- Name: factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.factura (
    id integer NOT NULL,
    numero_factura character varying(20) NOT NULL,
    fecha_emision timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    total numeric(10,2) NOT NULL,
    metodo_pago character varying(20) NOT NULL,
    estado boolean DEFAULT true,
    orden_id integer NOT NULL
);


ALTER TABLE public.factura OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16583)
-- Name: factura_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.factura_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.factura_id_seq OWNER TO postgres;

--
-- TOC entry 5018 (class 0 OID 0)
-- Dependencies: 227
-- Name: factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.factura_id_seq OWNED BY public.factura.id;


--
-- TOC entry 224 (class 1259 OID 16475)
-- Name: orden; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orden (
    id bigint NOT NULL,
    cliente_id bigint NOT NULL,
    fecha date NOT NULL,
    total double precision NOT NULL,
    estado boolean DEFAULT true,
    estado_proceso character varying(20) DEFAULT 'PENDIENTE'::character varying
);


ALTER TABLE public.orden OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16474)
-- Name: orden_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.orden ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.orden_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 226 (class 1259 OID 16560)
-- Name: orden_producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orden_producto (
    id bigint NOT NULL,
    orden_id bigint NOT NULL,
    producto_id bigint NOT NULL,
    cantidad integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.orden_producto OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16559)
-- Name: orden_producto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orden_producto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orden_producto_id_seq OWNER TO postgres;

--
-- TOC entry 5019 (class 0 OID 0)
-- Dependencies: 225
-- Name: orden_producto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orden_producto_id_seq OWNED BY public.orden_producto.id;


--
-- TOC entry 220 (class 1259 OID 16456)
-- Name: producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto (
    id bigint NOT NULL,
    nombre character varying(100) NOT NULL,
    precio double precision NOT NULL,
    disponible boolean NOT NULL,
    estado boolean DEFAULT true
);


ALTER TABLE public.producto OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16455)
-- Name: producto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.producto ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.producto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 4835 (class 2604 OID 16587)
-- Name: factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura ALTER COLUMN id SET DEFAULT nextval('public.factura_id_seq'::regclass);


--
-- TOC entry 4833 (class 2604 OID 16563)
-- Name: orden_producto id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_producto ALTER COLUMN id SET DEFAULT nextval('public.orden_producto_id_seq'::regclass);


--
-- TOC entry 5006 (class 0 OID 16466)
-- Dependencies: 222
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id, nombre, correo, telefono, estado) FROM stdin;
2	María Gómez	maria@gmail.com	0987654321	t
3	Carlos Ruiz	carlos@gmail.com	0971122334	t
4	Diego Bedoya	diegobedoya@gmail.com	0983764637	t
5	Federico	fede@gmail.com	099575465	t
1	Juan Pérez	juan@gmail.com	0991234567	f
\.


--
-- TOC entry 5012 (class 0 OID 16584)
-- Dependencies: 228
-- Data for Name: factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.factura (id, numero_factura, fecha_emision, total, metodo_pago, estado, orden_id) FROM stdin;
1	FAC-000001	2026-04-14 16:04:19.931546	1325.50	TRANSFERENCIA	t	1
2	FAC-000002	2026-04-14 16:04:19.931546	1225.50	TARJETA	t	2
3	FAC-000003	2026-04-14 16:04:19.931546	155.99	EFECTIVO	t	3
\.


--
-- TOC entry 5008 (class 0 OID 16475)
-- Dependencies: 224
-- Data for Name: orden; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orden (id, cliente_id, fecha, total, estado, estado_proceso) FROM stdin;
1	1	2026-04-10	1325.5	t	COMPLETADA
2	2	2026-04-11	260	t	COMPLETADA
3	3	2026-04-12	1200	t	COMPLETADA
4	1	2026-04-14	1225.5	t	COMPLETADA
5	1	2026-04-14	155.99	t	COMPLETADA
6	1	2026-04-14	0	t	COMPLETADA
7	4	2026-04-14	1305.5	t	COMPLETADA
8	1	2026-04-14	1305.5	t	COMPLETADA
9	2	2026-04-14	340	t	PENDIENTE
10	3	2026-04-14	340	t	PENDIENTE
\.


--
-- TOC entry 5010 (class 0 OID 16560)
-- Dependencies: 226
-- Data for Name: orden_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orden_producto (id, orden_id, producto_id, cantidad) FROM stdin;
1	1	1	1
2	1	2	1
3	1	3	1
4	2	2	1
5	2	4	1
6	2	5	1
7	3	1	1
8	3	5	1
9	3	6	1
10	4	1	1
11	4	2	1
12	5	6	1
13	5	10	1
14	7	1	1
15	7	2	1
16	7	3	1
17	8	1	1
18	8	2	1
19	8	3	1
20	9	3	2
21	9	4	1
22	10	3	2
23	10	4	1
\.


--
-- TOC entry 5004 (class 0 OID 16456)
-- Dependencies: 220
-- Data for Name: producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.producto (id, nombre, precio, disponible, estado) FROM stdin;
3	Teclado Mecánico	80	t	t
4	Monitor Samsung 24"	180	t	t
5	Impresora HP	150	f	t
6	Disco SSD 1TB	110	t	t
10	Teclado mecánico	45.99	t	t
1	Laptop Lenovo	1200	f	t
11	cafe	5.99	t	t
2	Mouse Logitech	25.5	f	t
\.


--
-- TOC entry 5020 (class 0 OID 0)
-- Dependencies: 221
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_seq', 5, true);


--
-- TOC entry 5021 (class 0 OID 0)
-- Dependencies: 227
-- Name: factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.factura_id_seq', 3, true);


--
-- TOC entry 5022 (class 0 OID 0)
-- Dependencies: 223
-- Name: orden_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orden_id_seq', 10, true);


--
-- TOC entry 5023 (class 0 OID 0)
-- Dependencies: 225
-- Name: orden_producto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orden_producto_id_seq', 23, true);


--
-- TOC entry 5024 (class 0 OID 0)
-- Dependencies: 219
-- Name: producto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producto_id_seq', 11, true);


--
-- TOC entry 4841 (class 2606 OID 16473)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 4847 (class 2606 OID 16598)
-- Name: factura factura_numero_factura_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_numero_factura_key UNIQUE (numero_factura);


--
-- TOC entry 4849 (class 2606 OID 16600)
-- Name: factura factura_orden_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_orden_id_key UNIQUE (orden_id);


--
-- TOC entry 4851 (class 2606 OID 16596)
-- Name: factura factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_pkey PRIMARY KEY (id);


--
-- TOC entry 4843 (class 2606 OID 16483)
-- Name: orden orden_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden
    ADD CONSTRAINT orden_pkey PRIMARY KEY (id);


--
-- TOC entry 4845 (class 2606 OID 16570)
-- Name: orden_producto orden_producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_producto
    ADD CONSTRAINT orden_producto_pkey PRIMARY KEY (id);


--
-- TOC entry 4839 (class 2606 OID 16464)
-- Name: producto producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (id);


--
-- TOC entry 4855 (class 2606 OID 16601)
-- Name: factura fk_factura_orden; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT fk_factura_orden FOREIGN KEY (orden_id) REFERENCES public.orden(id) ON DELETE RESTRICT;


--
-- TOC entry 4853 (class 2606 OID 16571)
-- Name: orden_producto fk_orden; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_producto
    ADD CONSTRAINT fk_orden FOREIGN KEY (orden_id) REFERENCES public.orden(id);


--
-- TOC entry 4852 (class 2606 OID 16484)
-- Name: orden fk_orden_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden
    ADD CONSTRAINT fk_orden_cliente FOREIGN KEY (cliente_id) REFERENCES public.cliente(id);


--
-- TOC entry 4854 (class 2606 OID 16576)
-- Name: orden_producto fk_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_producto
    ADD CONSTRAINT fk_producto FOREIGN KEY (producto_id) REFERENCES public.producto(id);


-- Completed on 2026-04-14 16:58:06

--
-- PostgreSQL database dump complete
--

\unrestrict qWO9AgPn5jWbYj4udq7p6UUcaTOLPj2wJczDkIvVoN4BIwmOXBgwxCClO1sfjZT

