import { Injectable, NotFoundException } from '@nestjs/common'
import { UserRepository } from './user.repository'

@Injectable()
export class UserService {
  constructor(private readonly userRepository: UserRepository) {}

  async getUser(username: string) {
    const user = await this.userRepository.getUser(username)
    if (!user) {
      throw new NotFoundException('user does not exist')
    }
    return user
  }
}
