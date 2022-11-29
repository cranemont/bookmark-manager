import {
  Body,
  Controller,
  InternalServerErrorException,
  Post,
} from '@nestjs/common'
import { AuthService } from './auth.service'
import { LoginDto } from './dto/login.dto'

@Controller()
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('login')
  async login(@Body() loginDto: LoginDto) {
    try {
    } catch (error) {
      throw new InternalServerErrorException()
    }
  }
}
