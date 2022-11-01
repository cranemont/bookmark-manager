import { Test, TestingModule } from '@nestjs/testing'
import { OneAIService } from 'src/oneai/oneai.service'
import { NlpService } from './nlp.service'

describe('NlpService', () => {
  let service: NlpService

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [NlpService, { provide: OneAIService, useValue: {} }],
    }).compile()

    service = module.get<NlpService>(NlpService)
  })

  it('should be defined', () => {
    expect(service).toBeDefined()
  })

  describe('summarizeByUrl', () => {
    it('should call the OneAI SDK', () => {
      test.todo
    })
  })
})
