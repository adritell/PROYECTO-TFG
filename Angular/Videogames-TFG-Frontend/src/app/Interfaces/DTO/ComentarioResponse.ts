import { UsuarioResponse } from "./UsuarioResponse";

export interface ComentarioResponse {
  id: number;
  text: string;
  usuario: UsuarioResponse;
  fecha: string;

}
