package stromkafka;

/**
 * @author bojue
 *
 */
public class test {

	public static void main(String[] args) {
		int a[]={6,8,4,1,2,5,3};
		mp(a);
//		insetSort(a);
		
//		for(int v:a){
//			System.out.print(v);
//		}
		
		int v=search(a,8);
		System.out.println(v);
	}
	
	
	/**
	 * 冒泡
	 * @param a
	 */
	public static void mp(int a[] ){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<(a.length-i-1);j++){
				if (a[j]>a[j+1]){
					int temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
	}
	
	
	/**
	 * 插入排序
	 * @param a
	 */
	public static void insetSort(int a[]){
		for(int i=1;i<a.length;i++){
			int j=i;
			int val=a[i];
			while(j>0 && a[j-1]>val){ 
				a[j]=a[j-1];
				j--;
			}
			a[j]=val;
		}
	}
	
	
	

	/**
	 * 二分查找
	 * @param a
	 * @param n
	 * @return
	 */
	public static int  search(int a[],int n){
		
		int low=0;
		int upper=a.length-1;
		int mid=0;
		while(low<=upper){
			mid=(low+upper)/2;
			if(n>a[mid]){
				
				low=mid+1;
			}else if(n<a[mid]){
				upper=mid-1;
			}else{
				return mid;
			}
		}
		return -1;
	}
}
