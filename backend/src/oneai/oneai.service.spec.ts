import { Test, TestingModule } from '@nestjs/testing'
import { OneAIService } from './oneai.service'

describe('OneaiService', () => {
  let service: OneAIService

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [{ provide: OneAIService, useValue: {} }],
    }).compile()

    service = module.get<OneAIService>(OneAIService)
  })

  it('should be defined', () => {
    expect(service).toBeDefined()
  })
})
