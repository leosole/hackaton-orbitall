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
    if(!cardIsValid(canonical.cardNumber)){
      const message = 'Erro ao alterar card. Número inválido'
      console.log(message)
      response.status(400)
      response.json({ 'mensagem': message })
    } else {
      neDB.update({ _id:request.params.id }, canonical, (exception, card) => {
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
  if(!cardIsValid(canonical.cardNumber)){
    const message = 'Erro ao salvar card. Número inválido'
    console.log(message)
    response.status(400)
    response.json({ 'mensagem': message })
  } else if(!checkFields(canonical)){
    const message = 'Erro ao salvar card. Cheque os campos'
    console.log(message)
    response.status(400)
    response.json({ 'mensagem': message })
  } else {
    neDB.insert(canonical, (exception, card) => {
        if (exception) {
            const message = 'Erro ao salvar card'
            console.log(message, exception)
            response.status(exception.status | 400)
            response.json({ 'mensagem': message })
        }
        response.status(201)
        response.json(card)
    })
  }
}

function cardIsValid(cardNumber) {
  if(typeof cardNumber == "string" && !isNaN(cardNumber)){
    cardNumber = cardNumber.replace(/\s/g,'') // remover espaços
    if(cardNumber.length == 16){
      return true
    }
  }
  return false
}

function checkFields(card) {
  var fields = ["cardNumber",
                "embossName",
                "customerName",
                "documentNumber",
                "motherName",
                "address",
                "city"]
  if (Object.keys(card).length != 7){
    return false
  }
  return fields.every(item => card.hasOwnProperty(item))
}

module.exports = api
