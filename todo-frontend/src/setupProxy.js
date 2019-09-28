const proxy = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(proxy('/api', {
    target: 'http://localhost:8080/',
    logLevel: 'debug',
    changeOrigin: true,
    secure: false,
  }));
};
