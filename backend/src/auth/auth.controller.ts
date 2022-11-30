import {
  Body,
  Controller,
  HttpException,
  InternalServerErrorException,
  Post,
  Req,
  UseGuards,
} from '@nestjs/common'
import { AuthService } from './auth.service'
import { RegisterUserDto } from './dto/register-user.dto'
import { LocalAuthGuard } from './guards/local.guard'

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @UseGuards(LocalAuthGuard)
  @Post('login')
  async login() {
    return 'success'
  }

  @Post('register')
  async register(@Body() registerUserDto: RegisterUserDto) {
    try {
      return await this.authService.registerUser(registerUserDto)
    } catch (error) {
      console.log(error)
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }
}
