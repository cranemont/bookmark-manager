import Joi from 'joi'
import { Output } from 'oneai/lib/src/classes'

export class SummarizeUrlResponseDto {
  url: string
  title: string
  // thumbnail: string
  tags: Array<string>
  summary: string

  constructor(url: string, oneAIResponse: Output) {
    this.url = url
    this.title = Joi.attempt(
      oneAIResponse.htmlFields.find((field) => field.name === 'title').value,
      Joi.string().allow('').default(null),
    )
    // this.thumbnail = Joi.attempt(
    //   oneAIResponse.htmlFields.find((field) => field.name === 'thumbnail')
    //     .value,
    //   Joi.string().allow('').default(null),
    // )
    this.tags = Joi.attempt(
      oneAIResponse.topics.map((topic) => topic.value),
      Joi.array().items(Joi.string().allow('')).default([]),
    )
    this.summary = Joi.attempt(
      oneAIResponse.summary.text,
      Joi.string().allow('').default(null),
    )
  }
}
