import { Injectable, NotFoundException } from '@nestjs/common'
import { UserRepository } from './user.repository'

@Injectable()
export class UserService {
  constructor(private readonly userRepository: UserRepository) {}

  async getCredentialByUsername(username: string) {
    return await this.userRepository.getCredentialByUsername(username)
  }

  async getById(id: string) {
    return await this.userRepository.getById(id)
  }

  async isUniqueUsername(username: string) {
    return !(await this.userRepository.count(username))
  }

  async createUser(username: string, hashedPassword: string) {
    return await this.userRepository.create(username, hashedPassword)
  }
}
