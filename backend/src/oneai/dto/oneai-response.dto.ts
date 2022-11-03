import Joi from 'joi'
import { Output } from 'oneai/lib/src/classes'

export class SummarizeUrlResponseDto {
  title: string
  thumbnail: string
  tags: Array<string>
  summary: string

  constructor(output: Output) {
    this.title = Joi.attempt(
      output.htmlFields.find((field) => field.name === 'title').value,
      Joi.string().allow('').default(null),
    )
    this.thumbnail = Joi.attempt(
      output.htmlFields.find((field) => field.name === 'thumbnail').value,
      Joi.string().allow('').default(null),
    )
    this.tags = Joi.attempt(
      output.topics.map((topic) => topic.value),
      Joi.array().items(Joi.string().allow('')).default([]),
    )
    this.summary = Joi.attempt(
      output.summary.text,
      Joi.string().allow('').default(null),
    )
  }
}
