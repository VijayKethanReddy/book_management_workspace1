export enum BookCategory {
    ADVENTURE,
    ACTION
}

export default class Book{
    constructor(public id: number, public logo: String, public title: String, public category: BookCategory, public price: number, public authorUserName: String, public authorName: String, public publisher: String, public publishedDate: Date, public content: String, public active: Boolean){ }
}