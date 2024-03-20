/*Таблица справочника товаров (Поиск товара)*/
const productListTable = {
    template: `<div style="font-size: 10pt;">
                    <el-table :data="tableData_" stripe border style="width: 100%;cursor: pointer;font-size: 10pt;" @row-dblclick="handleFunction">
                       <el-table-column sortable prop="photoLink" label="Фото" width="150" align="center">
                            <template slot-scope="scope">
                                <img class="border-sahdow" :src="scope.row.photoLink" style="width:73px; height:48px; object-fit: cover"/>
                            </template>
                       </el-table-column>
                       <el-table-column sortable prop="brandName" label="Бренд" width="160"></el-table-column>
                       <el-table-column sortable prop="goodsName" label="Наименование" width="200"></el-table-column>
                       <el-table-column sortable prop="commentCount" label="Количество отзывов" width="200"></el-table-column>
                       <el-table-column sortable prop="rating" label="Оценка товара"></el-table-column>
                       <el-table-column sortable prop="wildberriesId" label="Id маркетплейса"  ></el-table-column>
                       <el-table-column sortable prop="price" label="Цена"  width="100" ></el-table-column>
                    </el-table>
                    <popup-product-card ref="popupCard" v-bind:productDataL="productDataList"/>
<!--                    <el-pagination-->
<!--                     background-->
<!--                        style="display:flex; justify-content: center; margin-top:15px;"-->
<!--                        layout="prev, pager, next"-->
<!--                        :page-count="pageCount"-->
<!--                        @current-change="setPage">-->
<!--                       </el-pagination>-->
                    <el-pagination
                      background
                      style="display:flex; justify-content: center; margin-top:15px;"
                      @size-change="handleSizeChange"
                      @current-change="setPage"
                      :page-sizes="[10, 20, 30, 40, 50]"
                      :page-size="10"
                      layout="sizes, prev, pager, next"
                      :page-count="pageCount">
                    </el-pagination>
               </div>`
};
/*Попап карточка товара*/
const popupProductCard = {
    template: `
    <el-dialog :visible.sync="isVisable" @close="handlerCloseDialog"> 
     
        <div class="border-sahdow"> 
            <el-row>
                <el-col :span="24"><div class="grid-content bg-purple-dark product-card-name">{{name}}
                
                </div>
                
                </el-col>
            </el-row>  
            <el-row>
                <el-col :span="8">
                    <div class="grid-content bg-purple" style="display: flex; justify-content: center; align-items: center; height:200px;">
                        <el-image id="card-container-img" class="border-sahdow"
                                            style="width: auto; height: 200px"
                                            :src="url"
                                            :preview-src-list="urlList">
                        </el-image>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple">
                          <div class ="card-product-info" style="display: flex;flex-direction: column;">
                                <span class="span-little">Продавец: <a href="#">"{{brandName}}"</a></span>
                                <span class="price-in-card">{{priceWithSale}}₽</span>
                                <span class="card-info-span-weight">Продано более {{soldCount}} раз</span>
                          </div>  
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple-light">
                        <div class ="card-product-info" style="display: flex;flex-direction: column;">
                                <span class="span-little">Артикул: {{vendorCode}}</span>
                                <span class="card-info-span">Цена: {{priceWithoutSale}}₽</span>
                                <span class="card-info-span">Скидка: {{discount}}₽</span>
                            </div> 
                        </div>  
                </el-col>
            </el-row>
            <el-row style="padding-left: 210px; padding-bottom: 15px; justify-content:space-between; display:flex;">
                <el-col :span="4"><div class="grid-content bg-purple">Оценка: {{rating}}</div></el-col>
                <el-col :span="4"><div class="grid-content bg-purple-light">Отзывов: {{commentsCount}}</div></el-col>
                <el-col :span="12"><div class="grid-content bg-purple">Позиция в категории: {{position}}</div></el-col>
            </el-row>
        </div>
        <el-row>
            <el-col :span="24">
                <div id="content-sizes-box">
                <span>Таблица размеров</span>
                 <sizes-container2 :sizeslist="sizes" @toggle="handleFunction" ref="sizeCont"/>
                  <div class="button_favorites_add_delete">
                    <el-button class="button_favorites_add_delete" type="warning" icon="el-icon-star-off" circle  @click="handleAddToFavorites"></el-button>
                  </div>
<!--                  <div class="button_favorites_add_delete">-->
<!--                    <el-button  type="danger" icon="el-icon-delete" circle v-if="isFavorite" ></el-button>   -->
<!--                  </div>-->
                </div>
            </el-col>
        </el-row>
       <product-properties :properties="proplist" :descriptionProp="description"/>
       
    </el-dialog>`
};

/*Страница карточка товара*/
const productCardPage = {
    template: `
    <div>
        <div class="border-sahdow"> 
            <el-row>
                <el-col :span="24"><div class="grid-content bg-purple-dark product-card-name">{{name}}</div></el-col>
            </el-row>  
            <el-row>
                <el-col :span="8">
                    <div class="grid-content bg-purple" style="display: flex; justify-content: center; align-items: center; height:200px;">
                        <el-image id="card-container-img" class="border-sahdow"
                                            style="width: auto; height: 200px"
                                            :src="url"
                                            :preview-src-list="urlList">
                        </el-image>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple">
                          <div class ="card-product-info" style="display: flex;flex-direction: column;">
                                <span class="span-little">Продавец: <a href="#">"{{brandName}}"</a></span>
                                <span class="price-in-card">{{priceWithSale}}₽</span>
                                <span class="card-info-span-weight">Продано более {{soldCount}} раз</span>
                          </div>  
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple-light">
                        <div class ="card-product-info" style="display: flex;flex-direction: column;">
                                <span class="span-little">Артикул: {{vendorCode}}</span>
                                <span class="card-info-span">Цена: {{priceWithoutSale}}₽</span>
                                <span class="card-info-span">Скидка: {{discount}}₽</span>
                            </div> 
                        </div>  
                </el-col>
            </el-row>
            <el-row style="padding-left: 210px; padding-bottom: 15px; justify-content:space-between; display:flex;">
                <el-col :span="4"><div class="grid-content bg-purple">Оценка: {{rating}}</div></el-col>
                <el-col :span="4"><div class="grid-content bg-purple-light">Отзывов: {{commentsCount}}</div></el-col>
                <el-col :span="12"><div class="grid-content bg-purple">Позиция в категории: {{position}}</div></el-col>
            </el-row>
        </div>
        <el-row>
            <el-col :span="24">
                <div id="content-sizes-box">
                <span>Таблица размеров</span>
                 <sizes-container2 :sizeslist="sizes" @toggle="handleFunction" ref="sizeCont"/>
                </div>
            </el-col>
        </el-row>
       <product-properties :properties="proplist" :descriptionProp="description"/>
    </div>`
};

/*Размеры в карточке товара*/
const sizesContainer = {
    template: `
    <div id="sizes-container">
        <div class="border-sahdow" @click="clickedFunc(index,item.sizeName,item.price,item.quantity)"
                                          v-for="(item, index) in sizeslist"
                                          :key="item.index"
                                          :class="{ disabled:item.quantity == 0, active:index === setActive,container_mini:item.quantity !== 0}"
                                          @mouseover="changeColorMouseOver(index,item.quantity)"
                                          @mouseleave="changeColorMouseLeave(index,item.quantity)"
                                          >{{item.sizeName}}
        </div>
    </div>`
};
/*Свойства товара*/
const productProperties = {
    template: `
    <div class="border-sahdow" style="padding:20px;">
        <h1>Состав</h1>
        <p style="font-size: 8pt">{{description}}</p>
        <el-table :data="propList" stripe style="width: 100%">
            <el-table-column prop="key"></el-table-column>
            <el-table-column prop="value"></el-table-column>
        </el-table>
    </div>`
};

const userInfo ={
    template: `
    <div style="height:30px; display:flex; justify-content: center;align-items: center; color: #8b8b8b">
            {{userName}}
        </div>`
}
/*Сайдбар*/
const sideBar = {
    template: `
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <user-info/>
        <wb-nav/>
    </el-aside>`
};

/*Контент Динамики продаж*/
const salesDynamics = {
    template: `
    <wb-sales-dynamics-table/>`
};
/*Поиск контекстный*/
const liveSearch = {

    template: `<div style="    padding: 5px 5px 20px 5px; width: 80vw; display: flex; justify-content: center;"> <el-autocomplete
      class="inline-input"
      v-model="state"
      :fetch-suggestions="querySearchAsync"
      placeholder="Поиск"
      :trigger-on-focus="false"
      @select="handleSelect"
      style="width:300px;"
    ></el-autocomplete><!--<el-autocomplete
  v-model="state"
  :fetch-suggestions="querySearchAsync"
  placeholder="Please input"
  @select="handleSelect"
></el-autocomplete>-->
<popup-product-card ref="popupCard2" v-bind:productDataL="productDataList"/></div>`
};

/*Контент Остатков*/
const bitsAndPiecesTest = {

    template: `<el-empty description="Page Not Found"></el-empty>`
};
/*Страница Отстатки*/
const bitsAndPieces = {template: `<bits-and-pieces-test/>`};

/*Контент Динамики цен*/
const priceDynamics = {
    template: `<el-empty description="Page Not Found"></el-empty>`
};
/*Страница "Избранное"*/
const favoritesProductsPage ={
    template:`
    <favorites-products/>`,
}
/*Контент "Избранное"*/
const favoritesProductList={
    template:`
    <div class="favorites-products">
<!--        <div v-for="(item, index) in favoritesList" :key="item.index">-->
            <favorite-product-mini-card v-for="item in favList" :key="item.index" :productData="item" v-on:updateFav="updateFavorites"/>
<!--        </div>-->
    </div>`,
}
/*Мини карточка товара в "Избранное"*/
const favoriteProductMiniCard = {
    template:
        `<el-card class="box-card mini_card" style="width: 320px;">
              <div slot="header" class="clearfix">
                
                <el-button style="float: right; padding: 3px 0" type="text">Открыть</el-button>
              </div>
              <div class="mini_card_container">
                  <div class="mini_card_img_title_row">
                      <div class="mini_card_img_item">
                           <img class="border-sahdow" :src="productItemData.photoLink" style="width:73px; height:48px; object-fit: cover"/> 
                      </div>
                        
                       <div class="mini_card_title_item">
                           <span class="mini_card_item">{{productItemData.brandName}}</span> 
                           <span class="mini_card_item" >{{productItemData.wildberriesId}}</span>      
                       </div>      
                  </div>
                  <span class="mini_card_item">Цена: {{productItemData.price}}</span>
                  <span class="mini_card_item">Продажи: {{productItemData.soldCount}}</span>
                  <span class="mini_card_item">Остатки: {{productItemData.discount}}</span>
                  <div>
                        <el-button type="danger" icon="el-icon-delete" circle @click="handleRemoveFromFavorites"></el-button>
                  </div>  
              </div> 
                
            </el-card>`
}

/*Меню навигации*/
const wbNavigation = {
    template: `
    <el-menu background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b"
             :default-active="activeURL"
             router>
        <el-menu-item index="/"><i class="el-icon-tickets"></i></i><span>Справочник товаров</span></el-menu-item>
        <el-menu-item index="/sales-dynamics"><i class="el-icon-files"></i></i><span>Динамика продаж</span></el-menu-item>
        <el-menu-item index="/bits-and-pieces"><i class="el-icon-news"></i></i><span>Остатки</span></el-menu-item>
        <el-menu-item index="/price-dynamics"><i class="el-icon-coin"></i><span>Динамика цен</span></el-menu-item>
         <el-menu-item index="/favorites"><i class="el-icon-star-on"></i><span>Избранное</span></el-menu-item>
        
        <a href="/logout" style="text-decoration: none;">
            <el-menu-item><i class="el-icon-circle-close"></i><span>Выход</span></el-menu-item>
        </a>
    </el-menu>`,
};
/*Контент Справочника товаров*/
const productList = {
    template: `
     <div>
        <search-filter/>
        <el-main>
<!--        <options/>-->
            <product-list-table/>
        </el-main>
     </div>
`
};

const wbOptions = {
    template: `
    <div class="options" style="margin:20px;">
        <el-row type="flex" class="row-bg" justify="space-around">
            <el-col :span="6">
                <div class="grid-content bg-purple">
                    <el-select v-model="value" placeholder="Выбрать"  style="width:250px">
                        <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                     </el-select>
                </div>
            </el-col>
            <el-col :span="6">
                <div class="grid-content bg-purple">
<!--                    <el-input placeholder="Поиск" v-model="value" class="input-with-select">-->
<!--                        <el-button slot="append" icon="el-icon-search"></el-button>-->
<!--                    </el-input>-->
                </div>
            </el-col>
            <el-col :span="6">
                <div class="grid-content bg-purple">
                    <el-checkbox v-model="checked">Скрыть отсуствующие</el-checkbox>
                </div>
            </el-col>
        </el-row>       
    </div>`
};
/*Таблица Динамика продаж*/
const salesDynamicsTable = {
    template: `<div style="font-size: 10pt;">
                    <el-table :data="tableData_" stripe border style="width: 100%; cursor: pointer" @row-dblclick="handleFunction">
                       <el-table-column sortable prop="photoLink" label="Фото" width="150" align="center">
                            <template slot-scope="scope">
                                <img class="border-sahdow" :src="scope.row.photoLink" style="width:73px; height:48px; object-fit: cover"/>
                            </template>
                       </el-table-column>
                      
                       <el-table-column sortable prop="goodsName" label="Наименование" width="500"></el-table-column>
                       <el-table-column sortable prop="wildberriesId" label="Id маркетплейса" width="180" ></el-table-column>
                       <el-table-column sortable prop="soldCount" label="Реализованио" width="210"></el-table-column>
                       <el-table-column sortable prop="profit" label="Выручка"></el-table-column>
                    </el-table>
                    
                    <el-pagination
                     background
                        style="display:flex; justify-content: center; margin-top:15px;"
                        layout="prev, pager, next"
                        :total=831
                        @current-change="setPage">
                       </el-pagination>
               </div>`
};
/*Фильтрация*/
const searchFilter = {
    template:`
<div style="padding-top:20px;">
    <el-row type="flex" justify="space-around" style="padding:5px" align="middle">
        <el-col :span="8">
            <div>
                  <el-select
                            v-model="categoriesChosen"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="Выберите категории"
                            size="mini"
                            style = "width:360px;">
                        <el-option
                          v-for="item in categories"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                  </el-select>    
            </div>
        </el-col>
        <el-col :span="8">
            <div>
            <div class="demonstration-title">Цена</div>
               <span class="title-under-selection">Мин</span><el-input-number  size="mini" v-model="priceMin" controls-position="right" @change="handleChange" :min="0" :max="10000000"></el-input-number>
                <span class="title-under-selection">Макс</span><el-input-number size="mini" v-model="priceMax" controls-position="right" @change="handleChange" :min="0" :max="10000000"></el-input-number>
            </div>
        </el-col>
        <el-col :span="8">
            <div>
            <div class="demonstration-title">Оценка</div>
               <span class="title-under-selection">Мин</span><el-input-number  size="mini" v-model="rateMin" controls-position="right" @change="handleChange" :min="0" :max="10"></el-input-number>
                <span class="title-under-selection">Макс</span><el-input-number size="mini" v-model="rateMax" controls-position="right" @change="handleChange" :min="0" :max="10"></el-input-number>
            </div>
        </el-col>
    </el-row>
    <el-row type="flex" justify="space-around" style="padding:5px">
        <el-col :span="8">
        </el-col>
        <el-col :span="8">
            <div>
            <div class="demonstration-title">Количество продаж</div>
               <span class="title-under-selection">Мин</span><el-input-number  size="mini" v-model="soldMin" controls-position="right" @change="handleChange" :min="0" :max="10000000"></el-input-number>
                <span class="title-under-selection">Макс</span><el-input-number size="mini" v-model="soldMax" controls-position="right" @change="handleChange" :min="0" :max="10000000"></el-input-number>
            </div>
        </el-col>
        <el-col :span="8">
            <div>
                <div class="demonstration-title">Дата публикации на WB</div>
                <el-date-picker
                  v-model="dateRange"
                  type="daterange"
                  range-separator="по"
                  start-placeholder="начало"
                  end-placeholder="конец"
                  size="mini">
                </el-date-picker>
            </div>
        </el-col>
    </el-row>
    <div style="padding: 20px 0px 0 0; display: flex; justify-content: center;">
                <el-button @click="clickedButton">Подобрать товары</el-button>
        </div>
</div>
    `
}
