import { Injectable } from '@nestjs/common'
import { OneAIService } from 'src/oneai/oneai.service'

@Injectable()
export class NlpService {
  constructor(private readonly oneAIService: OneAIService) {}

  async summarizeUrl(url: string) {
    return await this.oneAIService.summarizeUrl(url)
  }
}
