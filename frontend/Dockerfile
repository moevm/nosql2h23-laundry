FROM node:18-alpine

WORKDIR /react-docker-example/

COPY build/ /react-docker-example/build

RUN npm install -g serve

EXPOSE 3000

CMD ["serve", "-s", "build"]