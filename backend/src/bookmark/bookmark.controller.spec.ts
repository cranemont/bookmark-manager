import { Test, TestingModule } from '@nestjs/testing'
import { BookmarkWebController } from './bookmark-web.controller'

describe('BookmarkController', () => {
  let controller: BookmarkWebController

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [BookmarkWebController],
    }).compile()

    controller = module.get<BookmarkWebController>(BookmarkWebController)
  })

  it('should be defined', () => {
    expect(controller).toBeDefined()
  })
})
