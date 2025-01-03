 CREATE TABLE _user (
     user_id BIGSERIAL PRIMARY KEY,
     name VARCHAR(75),
     password TEXT NOT NULL,
     email VARCHAR(75) NOT NULL,
     avatar TEXT,
     otp_code VARCHAR(10),
     otp_expiration_time TIMESTAMP,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP,
     status INTEGER NOT NULL
 );
CREATE INDEX user_idx ON _user (user_id);

 CREATE TABLE _role (
     role_id BIGSERIAL PRIMARY KEY,
     "name" VARCHAR(75) NOT NULL,
     description VARCHAR(255),

     create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     modified_date TIMESTAMP,
     status INTEGER NOT NULL
 );

 CREATE TABLE user_role (
     id BIGSERIAL PRIMARY KEY,
     user_id BIGINT NOT NULL,
     role_id BIGINT NOT null,

     CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES _role(role_id),
     CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES _user(user_id)
 );

CREATE TABLE nation (
    nation_id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(255),
    nation_code VARCHAR(10),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
CREATE INDEX nation_idx ON nation (nation_id);

CREATE TABLE city (
    city_id BIGSERIAL PRIMARY KEY,
    nation_id BIGINT NOT NULL,
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
CREATE INDEX city_idx ON city (city_id);

CREATE TABLE airport (
    airport_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    airport_code VARCHAR(10) NOT NULL,
    city_id BIGINT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT city_fk FOREIGN KEY (city_id) REFERENCES city(city_id)
);
CREATE INDEX airport_idx ON airport (airport_id);

create table plane (
	plane_id BIGSERIAL primary key,
	name VARCHAR(75) not null,
	code VARCHAR(10) not null,
	num_of_seats INTEGER,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
CREATE INDEX plane_idx ON plane (plane_id);

create table airline (
	airline_id BIGSERIAL primary key,
	name varchar(75) not null,
	code varchar(10) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
CREATE INDEX airline_idx ON airline (airline_id);

CREATE TABLE flight (
    flight_id BIGSERIAL PRIMARY KEY,
    plane_id BIGINT not null,
    airline_id BIGINT not null,
    from_airport_id BIGINT NOT NULL,
    to_airport_id BIGINT NOT NULL,
    departure_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP,
    base_price FLOAT,
    num_of_passengers INTEGER DEFAULT 1,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status INTEGER,
    constraint flight_plane_fk foreign key (plane_id) references plane(plane_id),
    constraint flight_airline_fk foreign key (airline_id) references airline(airline_id),
    constraint flight_from_airport_fk foreign key (from_airport_id) references airport(airport_id),
    constraint flight_to_airport_fk foreign key (to_airport_id) references airport(airport_id)
);

CREATE INDEX flight_idx ON flight (flight_id);


CREATE TABLE seat (
    seat_id BIGSERIAL PRIMARY key,
    plane_id BIGINT,
	class_level VARCHAR(15) not null,
	seat_code VARCHAR(10),
	available BOOLEAN default true,

	created_at TIMESTAMP default current_timestamp,
	updated_at timestamp,
	constraint seat_plane_fk foreign key (plane_id) references plane(plane_id)
);

CREATE INDEX seat_idx ON seat (seat_id);



CREATE TABLE passenger (
    passenger_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    first_name VARCHAR(75),
    last_name VARCHAR(75),
    birthday TIMESTAMP,
    gender VARCHAR(10),
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(75) NOT NULL,
    nationality_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN,

    constraint passenger_user_fk foreign key (user_id) references _user(user_id),
    constraint passenger_nationality_fk foreign key (nationality_id) references nation(nation_id)
);
CREATE INDEX passenger_idx ON passenger (passenger_id);

CREATE TABLE flight_seat_passenger (
    id BIGSERIAL PRIMARY KEY,
    flight_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,
    passenger_id BIGINT NOT NULL,
    CONSTRAINT flight_fk FOREIGN KEY (flight_id) REFERENCES flight(flight_id),
    CONSTRAINT seat_fk FOREIGN KEY (seat_id) REFERENCES seat(seat_id),
    CONSTRAINT passenger_fk FOREIGN KEY (passenger_id) REFERENCES passenger(passenger_id)
);
CREATE INDEX fsg_idx ON flight_seat_passenger (id);


CREATE TABLE contact (
    contact_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(75),
    last_name VARCHAR(75),
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(75) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN
);
CREATE INDEX contact_idx ON contact (contact_id);

CREATE TABLE booking (
    booking_id BIGSERIAL PRIMARY KEY,
    booking_code BIGINT,
    flight_id BIGINT NOT NULL,
    contact_id BIGINT,
    payment_method VARCHAR(10),
    ticket_number BIGINT,
    num_of_passengers INTEGER NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status INTEGER,

    CONSTRAINT flight_fk FOREIGN KEY (flight_id) REFERENCEs flight(flight_id),
    CONSTRAINT contact_fk FOREIGN KEY (contact_id) REFERENCES contact(contact_id)
);
CREATE INDEX booking_idx ON booking (booking_id);

CREATE TABLE invoice (
    invoice_id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    total_amount FLOAT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted BOOLEAN,
    CONSTRAINT booking_fk FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);
CREATE INDEX invoice_idx ON invoice (invoice_id);

CREATE TABLE booking_passenger (
    id BIGSERIAL PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    booking_id BIGINT NOT NULL,
    CONSTRAINT passenger_fk FOREIGN KEY (passenger_id) REFERENCES passenger(passenger_id),
    CONSTRAINT booking_fk FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);
CREATE INDEX booking_passenger_idx ON booking_passenger (id);







