import { ConflictException, Injectable } from '@nestjs/common'
import { Hasher } from 'src/common/hasher'
import { UserService } from 'src/user/user.service'
import { LoginDto } from './dto/login.dto'
import { RegisterUserDto } from './dto/register-user.dto'
import { AuthenticatedUser } from './authenticatedUser'
import { User } from '@prisma/client'

@Injectable()
export class AuthService {
  constructor(
    private readonly userService: UserService,
    private readonly hasher: Hasher,
  ) {}

  async validateUser(loginDto: LoginDto) {
    const user: User = await this.userService.getCredentialByUsername(
      loginDto.username,
    )
    if (user && (await this.hasher.compare(loginDto.password, user.password))) {
      return new AuthenticatedUser(user)
    }
    return null
  }

  async registerUser(registerUserDto: RegisterUserDto) {
    const { username, password } = registerUserDto
    if (!(await this.userService.isUniqueUsername(username))) {
      throw new ConflictException('username already exists')
    }

    return await this.userService.createUser(
      username,
      await this.hasher.hash(password),
    )
  }
}
