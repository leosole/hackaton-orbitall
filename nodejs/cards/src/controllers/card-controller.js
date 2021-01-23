const neDB = require('../configurations/database')
const api = {}

api.findAll = (request, response) => {
  neDB.find({}).sort({ name: 1 }).exec((exception, cards) => {
        if (exception) {
            const message = 'Erro ao listar cards'
            console.log(message, exception)
            response.status(exception.status | 400)
            response.json({ 'mensagem': message })
        }
        response.json(cards)
    })
}

api.findById = (request, response) => {
  console.log(request.params.id)
  neDB.find({ _id:request.params.id }, (exception, cards) => {
        if (exception) {
            const message = 'Erro ao procurar card ' + request.params.id
            console.log(message, exception)
            response.status(exception.status | 400)
            response.json({ 'mensagem': message })
        }
        response.json(cards)
    })
}

api.updateById = ( request, response) => {
    const canonical = request.body
    neDB.update({ _id:request.params.id }, canonical, (exception, card) => {
        if (exception) {
            const message = 'Erro ao salvar card'
            console.log(message, exception)
            response.status(exception.status | 400)
            response.json({ 'mensagem': message })
        }
        response.status(200)
        response.json(card)
    })
}

api.deleteById = ( request, response) => {
    neDB.remove({ _id:request.params.id }, {}, (exception, card) => {
        if (exception) {
            const message = 'Erro ao salvar card'
            console.log(message, exception)
            response.status(exception.status | 400)
            response.json({ 'mensagem': message })
        }
        response.status(200)
        response.json(card)
    })
}

api.save = (request, response) => {
  const canonical = request.body
  neDB.insert(canonical, (exception, card) => {
      if (exception) {
          const message = 'Erro ao alterar card'
          console.log(message, exception)
          response.status(exception.status | 400)
          response.json({ 'mensagem': message })
      }
      response.status(201)
      response.json(card)
  })
}
module.exports = api
