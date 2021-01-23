const api = require('../controllers/card-controller')

module.exports = (app) => {
    app.route('/cards')
        .get(api.findAll)
        .post(api.save),
    app.route('/cards/:id')
        .delete(api.deleteById)
        .put(api.updateById)
        .get(api.findById)
}
