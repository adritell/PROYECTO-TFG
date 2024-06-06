export interface TokenPayload {
  id:number;
  roles: string[];
  expiration: number;
  nombreUsuario: string;
  sub: string;
}

