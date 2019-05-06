FROM registry.gitlab.com/typokign/microservices/base:latest

WORKDIR /srv

COPY package*.json ./

RUN npm install

COPY . .

RUN make build

CMD ["make", "run"]
