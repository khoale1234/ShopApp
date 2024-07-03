import { Injectable } from "@angular/core";
import { Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { ProductService } from "./product.service";

@Injectable({
    providedIn: 'root',
})
export class CartService {
    private isBrowser: boolean;
    private cart: Map<number, number> = new Map();

    constructor(
        @Inject(PLATFORM_ID) private platformId: Object,
        private productService: ProductService
    ) {
        this.isBrowser = isPlatformBrowser(this.platformId);
        
        if (this.isBrowser) {
            const storedCart = localStorage.getItem('cart');
            if (storedCart) {
                this.cart = new Map(JSON.parse(storedCart));
            }
        }
    }

    addToCart(productId: number, quantity: number = 1) {
        debugger
        if (this.cart.has(productId)) {
            this.cart.set(productId, this.cart.get(productId)! + quantity);
        } else {
            this.cart.set(productId, quantity);
        }
        this.saveCartToLocalStorage();
    }

    getCart(): Map<number, number> {
        return this.cart;
    }

    private saveCartToLocalStorage(): void {
        if (this.isBrowser) {
            localStorage.setItem('cart', JSON.stringify(Array.from(this.cart.entries())));
        }
    }
    clearCart():void{
        this.cart.clear();
        this.saveCartToLocalStorage();
    }
}
