import { Test, TestingModule } from '@nestjs/testing'
import { OneaiService } from './oneai.service'

describe('OneaiService', () => {
  let service: OneaiService

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [OneaiService],
    }).compile()

    service = module.get<OneaiService>(OneaiService)
  })

  it('should be defined', () => {
    expect(service).toBeDefined()
  })
})
