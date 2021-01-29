//package br.com.drogaria.bean;
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//
//import org.primefaces.model.menu.DefaultMenuItem;
//import org.primefaces.model.menu.DefaultMenuModel;
//import org.primefaces.model.menu.DefaultSubMenu;
//import org.primefaces.model.menu.MenuModel;
//
//import br.com.drogaria.dao.MenuDAO;
//import br.com.drogaria.domain.Menu;
//
//@ManagedBean
//@SessionScoped
//public class MenuBean {
//
//	private MenuModel modeloDoMenu;
//	
//	public MenuModel getModeloDoMenu() {
//		return modeloDoMenu;
//	}
//	
//	public void setModeloDoMenu(MenuModel modeloDoMenu) {
//		this.modeloDoMenu = modeloDoMenu;
//	}
//	
//	
//	
//	@PostConstruct
//	public void iniciar() {
//		
//		MenuDAO menuDAO = new MenuDAO();
//
//		List<Menu> lista = menuDAO.listar();
//		
//		modeloDoMenu = new DefaultMenuModel();
//		
//		
//
//		for (Menu menu : lista) {
//
//			if (menu.getCaminho() == null) {
//				System.out.println(menu.getRotulo() + " - " + menu.getCaminho());
//				DefaultSubMenu subMenu = new DefaultSubMenu(menu.getRotulo());
//				for(Menu item : menu.getItensDoMenu()) {
//					System.out.println("\t" + item.getRotulo() + " - " + item.getCaminho());
//					
//					DefaultMenuItem menuItem = new DefaultMenuItem(item.getRotulo());
////					menuItem.setCommand(item.getAction());
//					menuItem.setAjax(false);
//					
//					menuItem.setUrl(item.getCaminho());
//					menuItem.setIcon(item.getIcone());
//					
//					
//					subMenu.addElement(menuItem);
//				}
//				
//				modeloDoMenu.addElement(subMenu);
//			}
//
//		}
//		
//		
//	}
//	
//	
//	
//}
