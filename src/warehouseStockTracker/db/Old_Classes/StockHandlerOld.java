package warehouseStockTracker.db.Old_Classes;
//
//
//package warehouseStockTracker.db;
//
//import warehouseStockTracker.db.StockDB;
//import warehouseStockTracker.db.TransactionDB;
//import warehouseStockTracker.business.InvalidStockOpException;
//import warehouseStockTracker.business.Stock;
//import warehouseStockTracker.business.Vendor;
//import warehouseStockTracker.business.Transaction;
//
//public class StockHandler{
//
//	// Stock In method
//	public static boolean stockIn(Stock stock, Vendor vendor, int quantity) throws DBException {
//		boolean status = false;
//		if(stock != null){
//			try{
//				stock.addStock(quantity);
//			}
//			catch (InvalidStockOpException e){
//				System.out.println(e);
//				return status;
//			}
//			Transaction transaction = new Transaction();
//			transaction.setStockId(stock.getId());
//			transaction.setVendorId(vendor.getId());
//			transaction.setType("stock_in");
//			transaction.setQuantity(quantity);
//			TransactionDB.add(transaction);
//			StockDB.update(stock);	
//			status = true;
//		}
//		return status;
//	}
//
//	// Stock out method
//	public static boolean stockOut(Stock stock, Vendor vendor, int quantity) throws DBException {
//		boolean status = false;
//		if(stock != null){
//			try{
//				stock.removeStock(quantity);
//			}
//			catch(InvalidStockOpException e){
//				System.out.println(e);
//				return status;
//			}
//			Transaction transaction = new Transaction();
//			transaction.setStockId(stock.getId());
//			transaction.setVendorId(vendor.getId());
//			transaction.setType("stock_out");
//			transaction.setQuantity(quantity);
//			TransactionDB.add(transaction);
//			StockDB.update(stock);	
//			status = true;
//		}
//		return status;
//	}
//
//	// Stock take method
//	public static boolean stockTake(Stock stock, int quantity) throws DBException {
//		boolean status = false;
//		try{
//			stock.removeStock(quantity);
//		}
//		catch(InvalidStockOpException e){
//			System.out.println(e);
//			return status;
//		}
//		Transaction transaction = new Transaction();
//		transaction.setStockId(stock.getId());
//		transaction.setQuantity(quantity);
//		transaction.setType("stock_take");
//		transaction.setVendorId(1);
//		TransactionDB.add(transaction);
//		StockDB.update(stock);
//		status = true;
//		return status;
//	}
//
//	// revert method
//	public static boolean revert(Transaction transaction) throws DBException {
//		boolean status = false;
//		if(transaction != null) {
//			int lastRecordId = TransactionDB.getLastRecord(transaction.getStockId());
//			if(lastRecordId == transaction.getId()){
//				Stock stock = StockDB.getByStockId(transaction.getStockId());
//				Transaction revertTransaction = new Transaction();
//				switch (transaction.getType()){
//				case "stock_in":
//					try{
//					stock.removeStock(transaction.getQuantity());
//					revertTransaction.setType("revert_stock_in");
//					}
//					catch(InvalidStockOpException e){
//						System.out.println(e);
//						return status;
//					}
//					break;
//				case "stock_out":
//					try {
//						stock.addStock(transaction.getQuantity());
//						revertTransaction.setType("revert_stock_out");
//					}
//					catch (InvalidStockOpException e) {
//						System.out.println(e);
//						return status;
//					}
//					break;
//				case "stock_take":
//					try {
//						stock.addStock(transaction.getQuantity());
//						revertTransaction.setType("revert_stock_take");
//					}
//					catch (InvalidStockOpException e) {
//						System.out.println(e);
//						return status;
//					}
//					break;
//				case "revert":
//					return status;	
//				}
//				revertTransaction.setStockId(transaction.getStockId());
//				revertTransaction.setQuantity(transaction.getQuantity());
//				revertTransaction.setVendorId(transaction.getVendorId());
//				StockDB.update(stock);
//				TransactionDB.add(revertTransaction);
//				status = true;
//			}
//		}
//		return status;
//	}
//}