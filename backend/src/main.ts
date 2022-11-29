import { NestFactory } from '@nestjs/core'
import { PrismaService } from './prisma/prisma.service'
import { AppModule } from './app.module'
import { ValidationPipe } from '@nestjs/common'
import session from 'express-session'

async function bootstrap() {
  const app = await NestFactory.create(AppModule)
  const prismaService = app.get(PrismaService)
  await prismaService.enableShutdownHooks(app)
  app.useGlobalPipes(new ValidationPipe())
  app.use(
    session({
      secret: 'my-secret',
      resave: false,
      saveUninitialized: false,
    }),
  )
  app.enableCors()

  await app.listen(3000)
}
bootstrap()
