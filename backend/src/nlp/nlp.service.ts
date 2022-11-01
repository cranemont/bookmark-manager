import { Injectable } from '@nestjs/common'
import { OneAIService } from 'src/oneai/oneai.service'

@Injectable()
export class NlpService {
  constructor(private readonly oneAIService: OneAIService) {}

  async summarizeByUrl(url: string) {
    const output = await this.oneAIService.summarizeUrl(url)
    return output
    return 'summarized Text'
  }
}
