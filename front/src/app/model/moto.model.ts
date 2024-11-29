import { Type } from "./type.model";
import { Image } from "./image.model";



export class Moto {
    idMotot! : number;
    nomMoto! : string;
    prixMoto! : number;
    dateCreation! : Date ;
    type! : Type;
    image! : Image
    imageStr!:string
    images!: Image[];

    }