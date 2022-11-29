import { Injectable } from '@nestjs/common'
import { plainToInstance } from 'class-transformer'
import { Hasher } from 'src/common/hasher'
import { UserService } from 'src/user/user.service'
import { AuthenticatedUser } from './authenticatedUser'

@Injectable()
export class AuthService {
  constructor(
    private readonly userService: UserService,
    private readonly hasher: Hasher,
  ) {}

  async validateUser(username: string, password: string) {
    const user = await this.userService.getUser(username)
    if (user.password && (await this.hasher.compare(password, user.password))) {
      return plainToInstance(AuthenticatedUser, user)
    }
    return null
  }
}
