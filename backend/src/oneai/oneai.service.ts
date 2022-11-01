import { Inject, Injectable } from '@nestjs/common'
import { AllowedSkill } from './oneai.allowedSkill'
// import OneAI from 'oneai'
// eslint-disable-next-line @typescript-eslint/no-var-requires
const OneAI = require('oneai')
import { Output } from 'oneai/lib/src/classes'
import { ConfigService } from '@nestjs/config'

@Injectable()
export class OneAIService {
  private readonly oneAI: typeof OneAI
  private readonly skillMapper: { [key in AllowedSkill]: any }

  constructor(private readonly config: ConfigService) {
    // private readonly skillMapper: { [key in AllowedSkill]: any }, // @Inject('ONEAI') private readonly oneAI: OneAI,
    this.oneAI = new OneAI(config.get('ONE_AI_API_KEY'))
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
        AllowedSkill.HtmlToArticle,
        AllowedSkill.Summarize,
        AllowedSkill.Keywords,

        // option 2
        // AllowedSkill.HtmlToArticle,
        // AllowedSkill.Topics,
        // AllowedSkill.Summarize,
      ],
      url,
    )
  }

  async summarizeText(text: string) {
    return await this.runPipeline([AllowedSkill.Summarize], text)
  }
}
