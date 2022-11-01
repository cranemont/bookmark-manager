import { Inject, Injectable } from '@nestjs/common'
import { ConfigService } from '@nestjs/config'

import { Output } from 'oneai/lib/src/classes'
import { AllowedSkill } from './oneai.allowedSkill'
import OneAI from 'oneai'

@Injectable()
export class OneAIService {
  private readonly skillMapper: { [key in AllowedSkill]: any }

  constructor(
    @Inject('OneAI') private readonly oneAI: OneAI,
    private readonly config: ConfigService,
  ) {
    this.skillMapper = {
      [AllowedSkill.HtmlToArticle]: this.oneAI.skills.htmlToArticle(),
      [AllowedSkill.Summarize]: this.oneAI.skills.summarize(),
      [AllowedSkill.Keywords]: this.oneAI.skills.keywords(),
      [AllowedSkill.Topics]: this.oneAI.skills.topics(),
    }
  }

  private async runPipeline(
    skills: AllowedSkill[],
    input: string,
  ): Promise<Output> {
    return await new this.oneAI.Pipeline(
      ...skills.map((skill) => this.skillMapper[skill]),
    ).run(input)
  }

  async summarizeUrl(url: string) {
    return await this.runPipeline(
      [
        // option 1
        // AllowedSkill.HtmlToArticle,
        // AllowedSkill.Summarize,
        // AllowedSkill.Keywords,

        // option 2
        AllowedSkill.HtmlToArticle,
        AllowedSkill.Topics,
        AllowedSkill.Summarize,
      ],
      url,
    )
  }

  async summarizeText(text: string) {
    return await this.runPipeline([AllowedSkill.Summarize], text)
  }
}
