import Joi from 'joi'
import { Output } from 'oneai/lib/src/classes'

export class SummarizeUrlResponseDto {
  title: string
  thumbnail: string
  topics: Array<string>
  summary: string

  constructor(output: Output) {
    this.title = Joi.attempt(
      output.htmlFields.find((field) => field.name === 'title').value,
      Joi.string().default(''),
    )
    this.thumbnail = Joi.attempt(
      output.htmlFields.find((field) => field.name === 'thumbnail').value,
      Joi.string().default(''),
    )
    this.topics = Joi.attempt(
      output.topics.map((topic) => topic.value as string),
      Joi.array().items(Joi.string().default('')).default([]),
    )
    this.summary = Joi.attempt(
      output.summary.text as string,
      Joi.string().default(''),
    )
  }
}
